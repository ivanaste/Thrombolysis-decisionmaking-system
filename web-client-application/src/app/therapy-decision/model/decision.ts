import {DecisionStatus} from "./decision-status";
import {Patient} from "../../shared/model/patient";

export class Decision {
  constructor(
    public id: string,
    public status: DecisionStatus,
    public createdAt?: Date,
    public pacijent?: Patient,
  ) {
  }
}
