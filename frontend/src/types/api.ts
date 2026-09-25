export type User = {
  id: string;
  fullName: string;
  email: string;
  phone?: string | null;
  role: string;
};

export type AuthResponse = User & {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
  userId: string;
};

export type Farm = {
  id: string;
  farmName: string;
  village?: string | null;
  district?: string | null;
  state?: string | null;
  pincode?: string | null;
  areaAcres: number;
  soilType?: string | null;
  irrigationType?: string | null;
  latitude?: number | null;
  longitude?: number | null;
};

export type Soil = {
  id: string;
  farmId: string;
  soilType?: string | null;
  ph?: number | null;
  nitrogen?: number | null;
  phosphorus?: number | null;
  potassium?: number | null;
  organicCarbon?: number | null;
  electricalConductivity?: number | null;
  moisturePercentage?: number | null;
  notes?: string | null;
};

export type Crop = { id: string; name: string; category?: string | null };

export type CropRecord = {
  id: string;
  farmId: string;
  cropId: string;
  cropName: string;
  plantingDate: string;
  expectedHarvestDate?: string | null;
  season?: string | null;
  status?: string | null;
};

export type Weather = {
  temperatureCelsius?: number | null;
  humidityPercentage?: number | null;
  rainfallMm?: number | null;
  windSpeedKmh?: number | null;
  weatherCondition: string;
  observedAt: string;
  dailyMaximumCelsius?: number | null;
  dailyMinimumCelsius?: number | null;
  dailyPrecipitationMm?: number | null;
  source: "LIVE";
};

export type Recommendation = {
  id: string;
  category: "IRRIGATION" | "SOIL" | "WEATHER" | "CROP" | "GENERAL";
  severity: "INFO" | "LOW" | "MEDIUM" | "HIGH";
  title: string;
  message: string;
  reason: string;
  generatedAt: string;
};

export type RecommendationResponse = {
  farmId: string;
  prototypeNotice: string;
  recommendations: Recommendation[];
};

export type FarmerProfile = {
  id: string;
  village?: string | null;
  district?: string | null;
  state?: string | null;
  pincode?: string | null;
  farmingType?: string | null;
  irrigationType?: string | null;
  totalLandAcres?: number | null;
  farmingExperienceYears?: number | null;
};

export type DashboardData = {
  farmer: User;
  profile?: FarmerProfile | null;
  selectedFarm: Farm;
  soil?: Soil | null;
  currentCrop?: CropRecord | null;
  weather?: Weather | null;
  weatherMessage?: string | null;
  advisory: RecommendationResponse;
};

export type ApiError = {
  message?: string;
  validationErrors?: Record<string, string> | null;
};
