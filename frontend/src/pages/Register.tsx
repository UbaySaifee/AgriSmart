import { useState, type ChangeEvent, type FormEvent } from "react";
import { ArrowRight, Eye, EyeOff, Leaf, ShieldCheck, Sprout } from "lucide-react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/AuthContext";
import { getErrorMessage } from "../services/api";

export default function Register() {
  const { register, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ fullName: "", email: "", phone: "", password: "" });
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  if (isAuthenticated) return <Navigate to="/dashboard" replace />;

  async function handleSubmit(event: FormEvent) {
    event.preventDefault(); setLoading(true); setError("");
    try { await register(form); navigate("/dashboard", { replace: true }); }
    catch (requestError) { setError(getErrorMessage(requestError, "Unable to create your account.")); }
    finally { setLoading(false); }
  }

  const update = (key: keyof typeof form) => (event: ChangeEvent<HTMLInputElement>) => setForm({ ...form, [key]: event.target.value });

  return (
    <main className="auth-shell">
      <section className="auth-story">
        <Link to="/" className="brand brand-light"><span className="brand-mark"><Leaf size={22} /></span><span>AgriSmart<small>Farm decisions, connected</small></span></Link>
        <div className="auth-story-copy"><span className="eyebrow eyebrow-light"><Sprout size={15} /> Start with the farm you know</span><h1>A practical digital home for your farm.</h1><p>Add one farm, its soil profile and current crop. AgriSmart brings the pieces together into a simple daily view.</p><div className="trust-line"><ShieldCheck size={19} /><span>No fake AI claims—only clear, explainable prototype rules.</span></div></div>
        <p className="auth-caption">You can finish your farm profile after creating the account.</p>
      </section>
      <section className="auth-panel"><div className="auth-card auth-card-wide"><span className="auth-mobile-logo"><Leaf size={20} /> AgriSmart</span><p className="eyebrow">Create your account</p><h2>Let’s set up your farm</h2><p className="muted">It takes less than a minute to get started.</p>
        <form onSubmit={handleSubmit} className="form-stack form-grid">
          {error && <div className="form-error full-span" role="alert">{error}</div>}
          <label className="full-span">Full name<input required minLength={2} value={form.fullName} onChange={update("fullName")} placeholder="Rahul Farmer" /></label>
          <label>Email address<input required type="email" value={form.email} onChange={update("email")} placeholder="rahul@example.com" /></label>
          <label>Phone number<input type="tel" value={form.phone} onChange={update("phone")} placeholder="98765 43210" /></label>
          <label className="full-span">Password<div className="password-field"><input required minLength={8} autoComplete="new-password" type={showPassword ? "text" : "password"} value={form.password} onChange={update("password")} placeholder="At least 8 characters" /><button type="button" aria-label={showPassword ? "Hide password" : "Show password"} onClick={() => setShowPassword(!showPassword)}>{showPassword ? <EyeOff size={19} /> : <Eye size={19} />}</button></div></label>
          <button className="primary-button full-span" disabled={loading}>{loading ? "Creating account…" : <>Create account <ArrowRight size={18} /></>}</button>
        </form>
        <p className="auth-switch">Already registered? <Link to="/login">Sign in</Link></p>
      </div></section>
    </main>
  );
}
