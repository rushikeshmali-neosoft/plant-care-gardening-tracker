export interface CareGuide {
  id: number;
  plantType: string;
  category: string;
  title: string;
  content: string;
  createdAt: string;
}

export interface CareGuideSummary {
  id: number;
  plantType: string;
  category: string;
  title: string;
}
