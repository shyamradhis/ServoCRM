export interface ILead {
  id?: string;
  company?: string;
  first_name?: string;
  last_name?: string | null;
  email?: string;
  phone?: number | null;
}

export const defaultValue: Readonly<ILead> = {};
