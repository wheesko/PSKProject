import { Goal } from './goal';
import { LearningTopic } from './learningTopic';
import { LearningEvent } from './learningEvent';
import { Role } from './role';

export interface Worker {
	id: number | null;
	name: string;
	surname: string;
	role: Role | undefined;
	quarterConstraint: number;
	team: string;
	learnedTopics: LearningTopic[];
	goals: Goal[];
	learningEvents: LearningEvent[];
	manager: string;
	icon: number | null;
}