import {DecisionStatus} from "./decision-status";
import {Patient} from "../../shared/model/patient";

export interface Decision {
  id: string,
  status: DecisionStatus,
  createdAt?: Date,
  pacijent?: Patient,
}
