#!/usr/bin/env bash
set -euo pipefail

API_BASE_URL="${API_BASE_URL:-http://localhost:18082/api}"
E2E_PASSWORD="${E2E_PASSWORD:?Set E2E_PASSWORD to a test-only password of at least 8 characters}"
RUN_ID="$(date +%s)"
EMAIL="e2e-${RUN_ID}@example.com"
OTHER_EMAIL="e2e-other-${RUN_ID}@example.com"

request() {
  local method="$1"
  local path="$2"
  local token="${3:-}"
  local body="${4:-}"
  local args=(-sS -X "$method" -H "Content-Type: application/json")
  if [[ -n "$token" ]]; then args+=(-H "Authorization: Bearer ${token}"); fi
  if [[ -n "$body" ]]; then args+=(-d "$body"); fi
  curl "${args[@]}" "${API_BASE_URL}${path}"
}

health="$(request GET /health)"
[[ "$(jq -r '.status' <<<"$health")" == "UP" ]]

protected_status="$(curl -sS -o /dev/null -w '%{http_code}' "${API_BASE_URL}/users/me")"
[[ "$protected_status" == "401" ]]

register_body="$(jq -n --arg email "$EMAIL" --arg password "$E2E_PASSWORD" '{fullName:"Rahul Farmer",email:$email,password:$password,phone:"9876543210"}')"
registration="$(request POST /auth/register "" "$register_body")"
token="$(jq -r '.accessToken' <<<"$registration")"
[[ -n "$token" && "$token" != "null" ]]

duplicate_status="$(curl -sS -o /dev/null -w '%{http_code}' -X POST -H "Content-Type: application/json" -d "$register_body" "${API_BASE_URL}/auth/register")"
[[ "$duplicate_status" == "409" ]]

login_body="$(jq -n --arg email "$EMAIL" --arg password "$E2E_PASSWORD" '{email:$email,password:$password}')"
login="$(request POST /auth/login "" "$login_body")"
[[ "$(jq -r '.accessToken' <<<"$login")" != "null" ]]

invalid_login_body="$(jq -n --arg email "$EMAIL" '{email:$email,password:"definitely-wrong-password"}')"
invalid_login_status="$(curl -sS -o /dev/null -w '%{http_code}' -X POST -H "Content-Type: application/json" -d "$invalid_login_body" "${API_BASE_URL}/auth/login")"
[[ "$invalid_login_status" == "401" ]]

farm_body='{"farmName":"Rahul Smart Farm","village":"Khandwa","district":"Khandwa","state":"Madhya Pradesh","pincode":"450001","areaAcres":5.5,"soilType":"Black soil","irrigationType":"DRIP","latitude":21.8257,"longitude":76.3526}'
farm="$(request POST /farms "$token" "$farm_body")"
farm_id="$(jq -r '.id' <<<"$farm")"
[[ -n "$farm_id" && "$farm_id" != "null" ]]

other_body="$(jq -n --arg email "$OTHER_EMAIL" --arg password "$E2E_PASSWORD" '{fullName:"Other Farmer",email:$email,password:$password}')"
other="$(request POST /auth/register "" "$other_body")"
other_token="$(jq -r '.accessToken' <<<"$other")"
ownership_status="$(curl -sS -o /dev/null -w '%{http_code}' -H "Authorization: Bearer ${other_token}" "${API_BASE_URL}/farms/${farm_id}")"
[[ "$ownership_status" == "404" ]]

soil_body='{"soilType":"Black soil","ph":6.7,"nitrogen":48,"phosphorus":32,"potassium":44,"organicCarbon":0.72,"electricalConductivity":0.42,"moisturePercentage":24}'
soil="$(request PUT "/farms/${farm_id}/soil" "$token" "$soil_body")"
[[ "$(jq -r '.farmId' <<<"$soil")" == "$farm_id" ]]

crops="$(request GET /crops "$token")"
crop_id="$(jq -r '.[0].id' <<<"$crops")"
crop_body="$(jq -n --arg cropId "$crop_id" '{cropId:$cropId,plantingDate:"2026-06-15",expectedHarvestDate:"2026-10-20",season:"Kharif",status:"GROWING"}')"
crop_record="$(request POST "/crops/farms/${farm_id}" "$token" "$crop_body")"
[[ "$(jq -r '.farmId' <<<"$crop_record")" == "$farm_id" ]]

weather="$(request GET "/farms/${farm_id}/weather/current" "$token")"
[[ "$(jq -r '.source' <<<"$weather")" == "LIVE" ]]

advisory="$(request GET "/farms/${farm_id}/recommendations" "$token")"
[[ "$(jq '.recommendations | length' <<<"$advisory")" -ge 3 ]]

dashboard="$(request GET /dashboard "$token")"
[[ "$(jq -r '.selectedFarm.id' <<<"$dashboard")" == "$farm_id" ]]
[[ "$(jq -r '.currentCrop.cropName' <<<"$dashboard")" != "null" ]]

jq -n \
  --arg status "PASS" \
  --arg farm "$farm_id" \
  --arg weatherSource "$(jq -r '.weather.source' <<<"$dashboard")" \
  --arg recommendations "$(jq -r '.advisory.recommendations | length' <<<"$dashboard")" \
  '{status:$status,farmId:$farm,weatherSource:$weatherSource,recommendations:$recommendations}'
