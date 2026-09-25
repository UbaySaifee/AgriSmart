import {
  Leaf,
  Mail,
  Phone,
  MapPin,
  ArrowUpRight,
} from "lucide-react";

export default function Footer() {
  return (
    <footer
      id="contact"
      className="bg-slate-900 text-white"
    >
      <div className="max-w-7xl mx-auto px-6 py-20">

        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-12">

          <div>

            <div className="flex items-center gap-3">

              <div className="bg-green-600 p-3 rounded-xl">

                <Leaf className="w-6 h-6" />

              </div>

              <div>

                <h2 className="text-3xl font-black">
                  AgriSmart
                </h2>

                <p className="text-slate-400 text-sm">
                  AI Powered Agriculture
                </p>

              </div>

            </div>

            <p className="mt-6 text-slate-400 leading-8">
              AgriSmart helps farmers with AI powered crop
              recommendation, disease detection, weather
              forecasting and live market prices.
            </p>

          </div>

          <div>

            <h3 className="text-xl font-bold mb-6">
              Product
            </h3>

            <ul className="space-y-4 text-slate-400">

              <li>Weather Forecast</li>

              <li>Crop Recommendation</li>

              <li>Disease Detection</li>

              <li>Market Prices</li>

            </ul>

          </div>

          <div>

            <h3 className="text-xl font-bold mb-6">
              Company
            </h3>

            <ul className="space-y-4 text-slate-400">

              <li>About</li>

              <li>Contact</li>

              <li>Careers</li>

              <li>Privacy Policy</li>

            </ul>

          </div>

          <div>

            <h3 className="text-xl font-bold mb-6">
              Contact
            </h3>

            <div className="space-y-5">

              <div className="flex items-center gap-3">

                <Mail className="text-green-500" />

                <span className="text-slate-400">
                  support@agrismart.ai
                </span>

              </div>

              <div className="flex items-center gap-3">

                <Phone className="text-green-500" />

                <span className="text-slate-400">
                  +91 9876543210
                </span>

              </div>

              <div className="flex items-start gap-3">

                <MapPin className="text-green-500 mt-1" />

                <span className="text-slate-400">
                  Indore, Madhya Pradesh, India
                </span>

              </div>

              <button className="mt-4 bg-green-600 hover:bg-green-700 transition px-6 py-3 rounded-xl flex items-center gap-2 font-semibold">

                Contact Us

                <ArrowUpRight size={18} />

              </button>

            </div>

          </div>

        </div>

        <div className="border-t border-slate-700 mt-16 pt-8 flex flex-col md:flex-row justify-between items-center gap-4">

          <p className="text-slate-500">
            © 2026 AgriSmart. All Rights Reserved.
          </p>

          <p className="text-slate-500">
            Built with ❤️ using React + TypeScript + Spring Boot
          </p>

        </div>

      </div>
    </footer>
  );
}