import Navbar from "../components/layout/Navbar";
import Hero from "../components/home/Hero";
import TrustedCompanies from "../components/home/TrustedCompanies";
import Features from "../components/home/Features";
import WeatherSection from "../components/home/WeatherSection";
import CropSection from "../components/home/CropSection";
import MarketSection from "../components/home/MarketSection";
import AISection from "../components/home/AISection";
import Statistics from "../components/home/Statistics";
import Testimonials from "../components/home/Testimonials";
import FAQ from "../components/home/FAQ";
import Newsletter from "../components/home/Newsletter";
import Footer from "../components/layout/Footer";

export default function Home() {
  return (
    <>
      <Navbar />
      <Hero />
      <TrustedCompanies />
      <Features />
      <WeatherSection />
      <CropSection />
      <MarketSection />
      <AISection />
      <Statistics />
      <Testimonials />
      <FAQ />
      <Newsletter />
      <Footer />
    </>
  );
}