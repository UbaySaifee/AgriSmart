import { Menu, X, Leaf } from "lucide-react";
import { useState } from "react";

export default function Navbar() {
  const [open, setOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50 bg-white/90 backdrop-blur-lg border-b border-green-100">
      <div className="max-w-7xl mx-auto px-6 h-20 flex items-center justify-between">

        <div className="flex items-center gap-3">
          <div className="bg-green-600 text-white p-2 rounded-xl">
            <Leaf size={22}/>
          </div>

          <div>
            <h1 className="text-2xl font-extrabold text-green-700">
              AgriSmart
            </h1>

            <p className="text-xs text-slate-500">
              AI Powered Farming
            </p>
          </div>
        </div>

        <nav className="hidden md:flex gap-8 font-semibold text-slate-700">
          <a href="#">Home</a>
          <a href="#features">Features</a>
          <a href="#weather">Weather</a>
          <a href="#market">Market</a>
          <a href="#ai">AI</a>
          <a href="#contact">Contact</a>
        </nav>

        <button className="hidden md:block bg-green-600 hover:bg-green-700 text-white px-6 py-3 rounded-xl font-bold">
          Get Started
        </button>

        <button
          className="md:hidden"
          onClick={() => setOpen(!open)}
        >
          {open ? <X/> : <Menu/>}
        </button>

      </div>

      {open && (
        <div className="md:hidden bg-white border-t p-5 space-y-4">

          <a href="#" className="block">Home</a>

          <a href="#features" className="block">Features</a>

          <a href="#weather" className="block">Weather</a>

          <a href="#market" className="block">Market</a>

          <a href="#ai" className="block">AI</a>

          <button className="w-full bg-green-600 text-white rounded-xl py-3">
            Get Started
          </button>

        </div>
      )}
    </header>
  );
}