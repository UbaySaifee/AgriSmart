import { ArrowRight } from "lucide-react";

export default function Newsletter() {
  return (
    <section className="py-24 bg-gradient-to-r from-green-700 to-green-600">

      <div className="max-w-5xl mx-auto px-6 text-center">

        <h2 className="text-5xl font-black text-white">

          Join 50,000+ Farmers

        </h2>

        <p className="text-green-100 mt-6 text-lg">

          Receive weather alerts, AI crop recommendations
          and farming tips directly in your inbox.

        </p>

        <div className="mt-10 flex flex-col md:flex-row gap-4 justify-center">

          <input
            type="email"
            placeholder="Enter your email"
            className="w-full md:w-96 px-6 py-4 rounded-2xl outline-none text-black"
          />

          <button className="bg-white text-green-700 font-bold px-8 rounded-2xl flex items-center justify-center gap-2">

            Subscribe

            <ArrowRight size={20}/>

          </button>

        </div>

      </div>

    </section>
  );
}