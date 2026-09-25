import { useState, type FormEvent } from "react";
import { ArrowRight, Eye, EyeOff, Leaf, ShieldCheck, Sprout } from "lucide-react";
import { Link, Navigate, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/AuthContext";
import { getErrorMessage } from "../services/api";

export default function Login() {
  const { login, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  if (isAuthenticated) return <Navigate to="/dashboard" replace />;

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setLoading(true);
    setError("");
    try {
      await login(email, password);
      const from = (location.state as { from?: string } | null)?.from;
      navigate(from ?? "/dashboard", { replace: true });
    } catch (requestError) {
      setError(getErrorMessage(requestError, "Unable to sign in. Check your email and password."));
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="auth-shell">
      <section className="auth-story">
        <Link to="/" className="brand brand-light"><span className="brand-mark"><Leaf size={22} /></span><span>AgriSmart<small>Farm decisions, connected</small></span></Link>
        <div className="auth-story-copy">
          <span className="eyebrow eyebrow-light"><Sprout size={15} /> Built for everyday farm decisions</span>
          <h1>Your field, soil and weather—finally in one place.</h1>
          <p>Turn scattered farm information into a clear plan for the day with live weather and explainable prototype advisories.</p>
          <div className="trust-line"><ShieldCheck size={19} /><span>Your farm data is protected with secure sign-in.</span></div>
        </div>
        <p className="auth-caption">Designed for a faster, calmer start to the farming day.</p>
      </section>

      <section className="auth-panel">
        <div className="auth-card">
          <span className="auth-mobile-logo"><Leaf size={20} /> AgriSmart</span>
          <p className="eyebrow">Welcome back</p>
          <h2>Sign in to your farm</h2>
          <p className="muted">See today’s farm picture and the actions that may need attention.</p>
          <form onSubmit={handleSubmit} className="form-stack">
            {error && <div className="form-error" role="alert">{error}</div>}
            <label>Email address<input required autoComplete="email" type="email" value={email} onChange={(event) => setEmail(event.target.value)} placeholder="farmer@example.com" /></label>
            <label>Password<div className="password-field"><input required minLength={8} autoComplete="current-password" type={showPassword ? "text" : "password"} value={password} onChange={(event) => setPassword(event.target.value)} placeholder="Enter your password" /><button type="button" aria-label={showPassword ? "Hide password" : "Show password"} onClick={() => setShowPassword(!showPassword)}>{showPassword ? <EyeOff size={19} /> : <Eye size={19} />}</button></div></label>
            <button className="primary-button" disabled={loading}>{loading ? "Signing in…" : <>Sign in <ArrowRight size={18} /></>}</button>
          </form>
          <p className="auth-switch">New to AgriSmart? <Link to="/register">Create an account</Link></p>
        </div>
      </section>
    </main>
  );
}
