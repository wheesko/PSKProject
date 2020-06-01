import { TopicByWorker } from "./topic-by-worker";

export interface WorkerWithTopics {
	id: number;
	manager: number;
	name: string;
	surname: string;
	topicsFuture: TopicByWorker[];
	topicsPast: TopicByWorker[];
}