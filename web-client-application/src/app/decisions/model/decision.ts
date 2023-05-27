import {DecisionStatus} from "./decision-status";
import {Patient} from "../../shared/model/patient";

export interface Decision {
  id: string,
  createdAt: Date,
  status: DecisionStatus,
  pacijent: Patient,
}
