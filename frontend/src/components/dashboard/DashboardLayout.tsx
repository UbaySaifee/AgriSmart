import type { ReactNode } from "react";
import { Bell } from "lucide-react";
import { Link, useLocation } from "react-router-dom";
import Sidebar from "./Sidebar";
import { useAuth } from "../../hooks/AuthContext";

const pageTitles: Record<string, string> = {
  "/dashboard": "Farm overview",
  "/farms": "My farms",
  "/soil": "Soil health",
  "/crops": "Current crop",
  "/recommendations": "Smart advisories",
  "/profile": "Farmer profile",
};

export default function DashboardLayout({ children }: { children: ReactNode }) {
  const { user } = useAuth();
  const { pathname } = useLocation();
  const pageTitle = pathname.startsWith("/farms/") ? "Farm details" : pageTitles[pathname] ?? "Farm workspace";

  return (
    <div className="app-shell">
      <Sidebar />
      <div className="app-main">
        <header className="app-header"><div><span className="header-kicker">AGRISMART WORKSPACE</span><strong>{pageTitle}</strong></div><div className="header-actions"><Link to="/recommendations" className="icon-button" aria-label="Open smart advisories"><Bell size={19} /><span /></Link><div className="header-user"><div className="avatar avatar-small">{user?.fullName?.charAt(0).toUpperCase()}</div><span>{user?.fullName}</span></div></div></header>
        <main className="page-content">{children}</main>
      </div>
    </div>
  );
}
