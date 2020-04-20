export interface LearningTopic {
	id: number;
	name: string;
	description: string;
	color: string | undefined;
	parentTopic: LearningTopic | null;
	createdBy: number;
}