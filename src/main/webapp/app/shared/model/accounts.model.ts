export interface IAccounts {
  id?: string;
  account_name?: string;
  phone?: number;
  website?: string;
}

export const defaultValue: Readonly<IAccounts> = {};
