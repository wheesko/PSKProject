import { LearningTopic } from './learningTopic';
import { Worker } from './worker';

export interface LearningEvent {
	id: number;
	name: string;
	description: string;
	dateTimeAt: string;
	learningTopic: LearningTopic;
	assignedWorker: Worker | null;
}