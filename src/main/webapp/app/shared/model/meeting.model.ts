import dayjs from 'dayjs';

export interface IMeeting {
  id?: string;
  title?: string | null;
  from?: dayjs.Dayjs;
  to?: dayjs.Dayjs | null;
  related_to?: string;
  host?: string | null;
}

export const defaultValue: Readonly<IMeeting> = {};
