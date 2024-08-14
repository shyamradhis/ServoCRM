import dayjs from 'dayjs';

export interface IDeals {
  id?: string;
  amount?: string | null;
  deal_name?: string;
  closing_date?: dayjs.Dayjs;
  account_name?: string | null;
  stage?: string | null;
}

export const defaultValue: Readonly<IDeals> = {};
