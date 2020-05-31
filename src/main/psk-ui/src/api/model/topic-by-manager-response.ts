export interface TopicByManagerResponse {
	id: number;
	manager: number;
	name: string;
	surname: string;
	topicsFuture: string[];
	topicsPast: string[];
}