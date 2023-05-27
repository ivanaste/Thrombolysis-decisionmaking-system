import {DecisionStatus} from "./decision-status";
import {Patient} from "./patient";

export interface Decision {
  id: string,
  status: DecisionStatus,
  createdAt?: Date,
  pacijent?: Patient,
}
