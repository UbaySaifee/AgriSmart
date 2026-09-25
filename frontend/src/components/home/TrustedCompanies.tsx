export default function TrustedCompanies() {
  const companies = [
    "Microsoft",
    "Google",
    "NVIDIA",
    "IBM",
    "Intel",
    "ISRO",
  ];

  return (
    <section className="py-16 bg-white">

      <div className="max-w-7xl mx-auto px-6">

        <p className="text-center text-slate-500 font-semibold uppercase tracking-widest">

          Trusted Technologies

        </p>

        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-8 mt-10">

          {companies.map((item) => (

            <div
              key={item}
              className="bg-slate-50 border rounded-2xl py-8 text-center font-bold text-slate-700 hover:bg-green-600 hover:text-white duration-300 shadow-sm"
            >

              {item}

            </div>

          ))}

        </div>

      </div>

    </section>
  ); 
}