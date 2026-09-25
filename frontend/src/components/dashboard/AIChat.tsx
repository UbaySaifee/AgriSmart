import { Bot, SendHorizontal } from "lucide-react";
import { useState } from "react";

export default function AIChat() {
  const [message, setMessage] = useState("");

  return (
    <div className="bg-white rounded-3xl shadow-lg p-8 h-full">

      <div className="flex items-center gap-3">

        <Bot className="text-green-600" size={34} />

        <div>

          <h2 className="text-2xl font-black">
            AI Farming Assistant
          </h2>

          <p className="text-slate-500">
            Ask anything about farming.
          </p>

        </div>

      </div>

      <div className="mt-8 rounded-2xl bg-slate-100 p-5 h-72 overflow-y-auto">

        <div className="bg-green-600 text-white rounded-2xl p-4 max-w-md">

          👋 Hello! I am AgriSmart AI.

          Ask me about crops, weather,
          fertilizers or diseases.

        </div>

      </div>

      <div className="flex gap-3 mt-6">

        <input
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="Ask AI anything..."
          className="flex-1 border rounded-xl px-5 py-4 outline-none"
        />

        <button
          className="bg-green-600 text-white px-6 rounded-xl"
        >

          <SendHorizontal size={20} />

        </button>

      </div>

    </div>
  );
}