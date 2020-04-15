import {Moment} from "moment";
import {LearningTopic} from "./learningTopic";
import {Worker} from "./worker";

export interface LearningEvent {
	id: number ,
	name: string,
	description: string,
	timeFrom: Moment | null,
	timeTo: Moment | null,
	learningTopic: LearningTopic,
	assignedWorker: Worker | null,
}