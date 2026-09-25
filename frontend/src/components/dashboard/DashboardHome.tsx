import { useEffect, useState } from "react";
import { ArrowRight, Cloud, CloudLightning, CloudRain, CloudSun, Droplets, FlaskConical, MapPin, Plus, RefreshCw, Ruler, Snowflake, Sparkles, Sprout, Sun, ThermometerSun, Wheat, Wind } from "lucide-react";
import { Link } from "react-router-dom";
import api, { getErrorMessage } from "../../services/api";
import type { DashboardData, Farm } from "../../types/api";
import { useAuth } from "../../hooks/AuthContext";

function greeting() {
  const hour = new Date().getHours();
  return hour < 12 ? "Good morning" : hour < 17 ? "Good afternoon" : "Good evening";
}

const value = (number?: number | null, suffix = "") => number == null ? "—" : `${Math.round(number * 10) / 10}${suffix}`;

export default function DashboardHome() {
  const { user } = useAuth();
  const [data, setData] = useState<DashboardData | null>(null);
  const [farms, setFarms] = useState<Farm[]>([]);
  const [selectedFarmId, setSelectedFarmId] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [needsFarm, setNeedsFarm] = useState(false);

  async function load(farmId?: string) {
    setLoading(true); setError("");
    try {
      const farmResponse = farms.length ? { data: farms } : await api.get<Farm[]>("/farms");
      setFarms(farmResponse.data);
      if (!farmResponse.data.length) { setNeedsFarm(true); setData(null); return; }
      const nextFarmId = farmId || selectedFarmId || farmResponse.data[0].id;
      setSelectedFarmId(nextFarmId);
      setData((await api.get<DashboardData>("/dashboard", { params: { farmId: nextFarmId } })).data);
      setNeedsFarm(false);
    }
    catch (requestError: unknown) {
      const message = getErrorMessage(requestError, "We could not load your dashboard.");
      if (message.toLowerCase().includes("create a farm")) setNeedsFarm(true); else setError(message);
    } finally { setLoading(false); }
  }
  // Dashboard initialization intentionally runs once; farm changes call load explicitly.
  // eslint-disable-next-line react-hooks/exhaustive-deps
  useEffect(() => { void load(); }, []);

  if (loading) return <DashboardSkeleton />;
  if (needsFarm) return <section className="onboarding-card"><div className="onboarding-icon"><Sprout size={32} /></div><span className="eyebrow">YOUR FIRST STEP</span><h1>Welcome, {user?.fullName?.split(" ")[0]}.</h1><p>Create your first farm to turn on the complete AgriSmart dashboard.</p><Link to="/farms" className="primary-button inline-button"><Plus size={18} /> Add your first farm</Link><div className="onboarding-steps"><span><b>1</b> Add farm location</span><span><b>2</b> Record soil & crop</span><span><b>3</b> Get daily advisories</span></div></section>;
  if (error || !data) return <div className="state-card error-state"><h2>Dashboard unavailable</h2><p>{error}</p><button className="secondary-button" onClick={() => void load(selectedFarmId)}><RefreshCw size={17} /> Try again</button></div>;

  const firstName = data.farmer.fullName.split(" ")[0];
  const farm = data.selectedFarm;
  const weather = data.weather;
  const summary = [
    { label: "Current crop", value: data.currentCrop?.cropName ?? "Not added", note: data.currentCrop?.status ?? "Add a crop record", icon: Wheat, color: "green" },
    { label: "Soil pH", value: value(data.soil?.ph), note: data.soil?.soilType ?? "Soil profile needed", icon: FlaskConical, color: "amber" },
    { label: "Temperature", value: value(weather?.temperatureCelsius, "°C"), note: weather?.weatherCondition ?? "Live weather unavailable", icon: ThermometerSun, color: "orange" },
    { label: "Soil moisture", value: value(data.soil?.moisturePercentage, "%"), note: data.soil ? "Latest recorded reading" : "Add a soil profile", icon: Droplets, color: "blue" },
    { label: "Farm area", value: value(farm.areaAcres, " ac"), note: farm.irrigationType?.replaceAll("_", " ") ?? "Irrigation not set", icon: Ruler, color: "purple" },
  ];

  return <>
    <section className="page-title-row"><div><p className="eyebrow">TODAY AT A GLANCE</p><h1>{greeting()}, {firstName}.</h1><p>Here is what is happening across <strong>{farm.farmName}</strong> today.</p></div><label className="farm-selector"><div className="mini-field"><Sprout size={18} /></div><span><small>Selected farm</small><select aria-label="Selected farm" value={selectedFarmId} onChange={(event) => void load(event.target.value)}>{farms.map((item) => <option value={item.id} key={item.id}>{item.farmName}</option>)}</select></span></label></section>
    <div className="location-line"><MapPin size={15} /> {[farm.village, farm.district, farm.state].filter(Boolean).join(", ")}</div>

    <section className="summary-grid">{summary.map(({ label, value: cardValue, note, icon: Icon, color }) => <article className="summary-card" key={label}><div className={`metric-icon ${color}`}><Icon size={21} /></div><div><span>{label}</span><strong>{cardValue}</strong><small>{note}</small></div></article>)}</section>

    <section className="dashboard-grid">
      <article className="weather-card panel-card">
        <div className="panel-heading"><div><p className="eyebrow">LIVE WEATHER</p><h2>Field conditions</h2></div>{weather && <span className="live-badge"><i /> LIVE</span>}</div>
        {weather ? <><div className="weather-main"><div className="weather-symbol"><WeatherIcon condition={weather.weatherCondition} /></div><div><strong>{value(weather.temperatureCelsius, "°")}</strong><span>{weather.weatherCondition}</span></div><div className="weather-range"><span>High <b>{value(weather.dailyMaximumCelsius, "°")}</b></span><span>Low <b>{value(weather.dailyMinimumCelsius, "°")}</b></span></div></div><div className="weather-details"><span><Droplets size={17} /><small>Humidity</small><b>{value(weather.humidityPercentage, "%")}</b></span><span><CloudRain size={17} /><small>Rain today</small><b>{value(weather.dailyPrecipitationMm, " mm")}</b></span><span><Wind size={17} /><small>Wind</small><b>{value(weather.windSpeedKmh, " km/h")}</b></span></div><p className="source-note">Open-Meteo observation · {new Date(weather.observedAt).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}</p></> : <div className="weather-unavailable"><CloudRain size={28} /><h3>Live weather unavailable</h3><p>{data.weatherMessage || "Live weather is temporarily unavailable."}</p></div>}
      </article>

      <article className="advisory-card panel-card"><div className="panel-heading"><div><p className="eyebrow">SMART ADVISORY</p><h2>What needs attention</h2></div><Link to="/recommendations">View all <ArrowRight size={16} /></Link></div>{data.advisory.recommendations.length ? <div className="advisory-list">{data.advisory.recommendations.slice(0, 3).map((item) => <Link to="/recommendations" className="advisory-item" key={item.id}><span className={`severity-dot ${item.severity.toLowerCase()}`} /><div><span>{item.category}</span><strong>{item.title}</strong><p>{item.message}</p></div><ArrowRight size={17} /></Link>)}</div> : <div className="advisory-empty"><Sparkles size={25} /><h3>No active advisories</h3><p>Recommendations will appear when enough farm information is available.</p></div>}<p className="prototype-note"><Sparkles size={15} /> {data.advisory.prototypeNotice}</p></article>
    </section>

    <section className="farm-overview panel-card"><div className="panel-heading"><div><p className="eyebrow">FARM OVERVIEW</p><h2>{farm.farmName}</h2></div><Link to={`/farms/${farm.id}`}>Open farm <ArrowRight size={16} /></Link></div><div className="overview-grid"><Overview label="Location" value={[farm.village, farm.district].filter(Boolean).join(", ") || "Not set"} /><Overview label="Current crop" value={data.currentCrop?.cropName ?? "Not added"} /><Overview label="Soil type" value={data.soil?.soilType ?? farm.soilType ?? "Not added"} /><Overview label="Irrigation" value={farm.irrigationType?.replaceAll("_", " ") ?? "Not set"} /><Overview label="Farm area" value={`${farm.areaAcres} acres`} /></div></section>
  </>;
}

function Overview({ label, value: overviewValue }: { label: string; value: string }) { return <div><span>{label}</span><strong>{overviewValue}</strong></div>; }
function WeatherIcon({ condition }: { condition: string }) {
  const normalized = condition.toLowerCase();
  if (normalized.includes("thunder")) return <CloudLightning size={40} />;
  if (normalized.includes("snow")) return <Snowflake size={40} />;
  if (normalized.includes("rain") || normalized.includes("drizzle")) return <CloudRain size={40} />;
  if (normalized.includes("cloud") || normalized.includes("overcast") || normalized.includes("fog")) return <CloudSun size={40} />;
  if (normalized.includes("clear")) return <Sun size={40} />;
  return <Cloud size={40} />;
}

function DashboardSkeleton() {
  return <div aria-label="Loading dashboard" aria-busy="true"><div className="skeleton skeleton-title" /><div className="skeleton-summary">{Array.from({ length: 5 }, (_, index) => <div className="skeleton skeleton-card" key={index} />)}</div><div className="skeleton-panels"><div className="skeleton skeleton-panel" /><div className="skeleton skeleton-panel" /></div></div>;
}
