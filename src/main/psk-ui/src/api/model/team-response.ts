import { EmployeeResponse } from "./employee-response";
import { LearningEvent } from "../../models/learningEvent";

interface EmployeeResponseWithLearningDays extends EmployeeResponse{
	learningDays: LearningEvent[];
}
export interface TeamResponse {
	TeamGoals: string[];
	TeamLead: EmployeeResponse
	workers: EmployeeResponseWithLearningDays[];
	id: number;
	name: string;
}