export interface TopicCreateRequest {
    description: string;
    parentTopicId: number | null;
    name: string;
}