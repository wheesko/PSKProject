import { LearningTopic } from './learningTopic';
import { Worker } from './worker';

export interface LearningEvent {
	id: number;
	name: string;
	description: string;
	learned: boolean;
	comment: string;
	dateTimeAt: string;
	topic: LearningTopic;
	assignedWorker: Worker | null;
}