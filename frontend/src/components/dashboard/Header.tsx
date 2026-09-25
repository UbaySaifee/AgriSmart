import {
  Bell,
  Search,
  CircleUserRound,
} from "lucide-react";

export default function Header() {
  return (
    <header className="bg-white rounded-3xl shadow-md px-8 py-5 flex items-center justify-between">

      <div>

        <h1 className="text-3xl font-black">

          Good Morning 👋

        </h1>

        <p className="text-slate-500 mt-1">

          Welcome back to AgriSmart Dashboard

        </p>

      </div>

      <div className="flex items-center gap-5">

        <div className="relative">

          <Search
            className="absolute left-4 top-4 text-slate-400"
            size={18}
          />

          <input
            placeholder="Search..."
            className="pl-11 pr-5 py-3 rounded-xl border outline-none w-72"
          />

        </div>

        <button className="relative">

          <Bell size={28} />

          <span className="absolute -top-1 -right-1 w-3 h-3 bg-red-500 rounded-full"></span>

        </button>

        <CircleUserRound
          size={42}
          className="text-green-600"
        />

      </div>

    </header>
  );
}