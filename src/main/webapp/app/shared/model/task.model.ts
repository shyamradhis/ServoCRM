export interface ITask {
  id?: string;
  task_owner?: string | null;
  subject?: string | null;
  due_date?: string | null;
  contact?: string | null;
  account?: string | null;
  status?: string | null;
  priority?: string | null;
  reminder?: string | null;
  repeat?: string | null;
}

export const defaultValue: Readonly<ITask> = {};
