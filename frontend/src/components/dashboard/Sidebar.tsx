import { LayoutDashboard, Map, FlaskConical, Wheat, Sparkles, UserRound, LogOut, Leaf, Menu, X } from "lucide-react";
import { NavLink, useNavigate } from "react-router-dom";
import { useState } from "react";
import { useAuth } from "../../hooks/AuthContext";

const navigation = [
  { to: "/dashboard", label: "Dashboard", icon: LayoutDashboard },
  { to: "/farms", label: "My farms", icon: Map },
  { to: "/soil", label: "Soil profile", icon: FlaskConical },
  { to: "/crops", label: "Current crop", icon: Wheat },
  { to: "/recommendations", label: "Advisories", icon: Sparkles },
  { to: "/profile", label: "Profile", icon: UserRound },
];

export default function Sidebar() {
  const [open, setOpen] = useState(false);
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const signOut = () => { logout(); navigate("/login"); };

  return (
    <>
      <button className="mobile-menu-button" onClick={() => setOpen(true)} aria-label="Open navigation"><Menu size={22} /></button>
      {open && <button className="nav-backdrop" aria-label="Close navigation" onClick={() => setOpen(false)} />}
      <aside className={`app-sidebar ${open ? "sidebar-open" : ""}`}>
        <div>
          <div className="sidebar-brand"><span><Leaf size={22} /></span><div>AgriSmart<small>Grow with clarity</small></div><button onClick={() => setOpen(false)} aria-label="Close navigation"><X size={20} /></button></div>
          <nav className="sidebar-nav" aria-label="Main navigation">
            <p>FARM WORKSPACE</p>
            {navigation.map(({ to, label, icon: Icon }) => <NavLink key={to} to={to} onClick={() => setOpen(false)} className={({ isActive }) => isActive ? "active" : ""}><Icon size={19} /><span>{label}</span></NavLink>)}
          </nav>
        </div>
        <div className="sidebar-account"><div className="avatar">{user?.fullName?.charAt(0).toUpperCase() || "F"}</div><div><strong>{user?.fullName}</strong><span>{user?.email}</span></div><button onClick={signOut} aria-label="Log out"><LogOut size={18} /></button></div>
      </aside>
    </>
  );
}
