import { useEffect, useState, type FormEvent } from "react";
import { ArrowRight, MapPin, Plus, Ruler, Sprout, X } from "lucide-react";
import { Link } from "react-router-dom";
import DashboardLayout from "../../components/dashboard/DashboardLayout";
import { Field, LoadingState, PageHeading } from "../../components/dashboard/PageElements";
import api, { getErrorMessage } from "../../services/api";
import type { Farm } from "../../types/api";

const initialFarm = { farmName: "", village: "", district: "", state: "", pincode: "", areaAcres: "", soilType: "", irrigationType: "DRIP", latitude: "", longitude: "" };

export default function Farms() {
  const [farms, setFarms] = useState<Farm[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [saving, setSaving] = useState(false);
  const [form, setForm] = useState(initialFarm);

  async function load() { setLoading(true); setError(""); try { setFarms((await api.get<Farm[]>("/farms")).data); } catch (requestError) { setError(getErrorMessage(requestError, "Could not load your farms.")); } finally { setLoading(false); } }
  useEffect(() => { void load(); }, []);
  const update = (key: keyof typeof form) => (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => setForm({ ...form, [key]: event.target.value });

  async function createFarm(event: FormEvent) {
    event.preventDefault(); setSaving(true); setError("");
    try {
      await api.post("/farms", { ...form, areaAcres: Number(form.areaAcres), latitude: form.latitude ? Number(form.latitude) : null, longitude: form.longitude ? Number(form.longitude) : null });
      setForm(initialFarm); setShowForm(false); await load();
    } catch (requestError) { setError(getErrorMessage(requestError, "Could not save this farm.")); }
    finally { setSaving(false); }
  }

  return <DashboardLayout><PageHeading eyebrow="FARM MANAGEMENT" title="My farms" description="Keep every field’s essential information in one reliable place." action={<button className="primary-button inline-button" onClick={() => setShowForm(true)}><Plus size={17} /> Add farm</button>} />
    {error && <div className="inline-alert">{error}</div>}
    {loading ? <LoadingState /> : farms.length === 0 ? <div className="empty-workspace"><div><Sprout size={30} /></div><h2>Plant the first digital marker</h2><p>Add your farm location and area to unlock live weather and the dashboard.</p><button className="primary-button inline-button" onClick={() => setShowForm(true)}><Plus size={17} /> Create first farm</button></div> : <div className="farm-grid">{farms.map((farm) => <Link to={`/farms/${farm.id}`} className="farm-card" key={farm.id}><div className="farm-card-top"><div className="mini-field"><Sprout size={19} /></div><span>ACTIVE</span></div><h2>{farm.farmName}</h2><p><MapPin size={14} /> {[farm.village, farm.district, farm.state].filter(Boolean).join(", ") || "Location not set"}</p><div className="farm-card-facts"><span><Ruler size={15} /><b>{farm.areaAcres} acres</b><small>Farm area</small></span><span><Sprout size={15} /><b>{farm.irrigationType?.replaceAll("_", " ") ?? "—"}</b><small>Irrigation</small></span></div><div className="card-link">View farm details <ArrowRight size={16} /></div></Link>)}</div>}
    {showForm && <div className="modal-backdrop"><section className="form-modal" role="dialog" aria-modal="true" aria-labelledby="farm-form-title"><div className="modal-heading"><div><p className="eyebrow">NEW FARM</p><h2 id="farm-form-title">Add a farm</h2></div><button onClick={() => setShowForm(false)} aria-label="Close"><X size={20} /></button></div><form className="record-form" onSubmit={createFarm}><Field label="Farm name"><input required value={form.farmName} onChange={update("farmName")} placeholder="Rahul Smart Farm" /></Field><Field label="Area (acres)"><input required min="0.01" step="0.01" type="number" value={form.areaAcres} onChange={update("areaAcres")} /></Field><Field label="Village"><input value={form.village} onChange={update("village")} /></Field><Field label="District"><input value={form.district} onChange={update("district")} /></Field><Field label="State"><input value={form.state} onChange={update("state")} /></Field><Field label="Pincode"><input inputMode="numeric" pattern="[0-9]{6}" value={form.pincode} onChange={update("pincode")} /></Field><Field label="Soil type"><input value={form.soilType} onChange={update("soilType")} placeholder="Black soil" /></Field><Field label="Irrigation"><select value={form.irrigationType} onChange={update("irrigationType")}><option>DRIP</option><option>RAINFED</option><option>SPRINKLER</option><option>CANAL</option><option>TUBE_WELL</option><option>MIXED</option></select></Field><Field label="Latitude"><input min="-90" max="90" step="any" type="number" value={form.latitude} onChange={update("latitude")} placeholder="21.8257" /></Field><Field label="Longitude"><input min="-180" max="180" step="any" type="number" value={form.longitude} onChange={update("longitude")} placeholder="76.3526" /></Field><div className="form-actions"><button type="button" className="secondary-button" onClick={() => setShowForm(false)}>Cancel</button><button className="primary-button inline-button" disabled={saving}>{saving ? "Saving…" : "Save farm"}</button></div></form></section></div>}
  </DashboardLayout>;
}
