export interface IContacts {
  id?: string;
  first_name?: string;
  last_name?: string | null;
  account_name?: string | null;
  email?: string | null;
  phone?: number | null;
}

export const defaultValue: Readonly<IContacts> = {};
