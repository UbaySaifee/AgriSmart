import { Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/dashboard/Dashboard";
import ProtectedRoute from "./components/dashboard/ProtectedRoute";
import Farms from "./pages/dashboard/Farms";
import FarmDetails from "./pages/dashboard/FarmDetails";
import Soil from "./pages/dashboard/Soil";
import Crops from "./pages/dashboard/Crops";
import Recommendations from "./pages/dashboard/Recommendations";
import Profile from "./pages/dashboard/Profile";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route element={<ProtectedRoute />}>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/farms" element={<Farms />} />
        <Route path="/farms/:farmId" element={<FarmDetails />} />
        <Route path="/soil" element={<Soil />} />
        <Route path="/crops" element={<Crops />} />
        <Route path="/recommendations" element={<Recommendations />} />
        <Route path="/profile" element={<Profile />} />
      </Route>
      <Route path="*" element={<RouteFallback />} />
    </Routes>
  );
}

function RouteFallback() {
  return <div className="grid min-h-screen place-items-center bg-[#f4f6ef] text-[#173c2a]">Page not found</div>;
}
