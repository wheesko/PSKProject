import { LearningTopic } from '../../models/learningTopic';

export interface TeamLearningDaysResponse {
    id: number;
    name: string;
    comment: string;
    dateTimeAt: string;
    learned: boolean;
    assignee: Assignee;
    topic: LearningTopic;
}

export interface Assignee {
    id: number;
    name: string;
    surname: string;
    email: string;
    role: {
        id: number;
        name: string;
    };
    managedTeam: Team;
    workingTeam: Team;
    quarterLearningDayLimit: number;
    consecutiveLearningDayLimit: number;
}

export interface Team {
    id: number;
    name: number;
}
