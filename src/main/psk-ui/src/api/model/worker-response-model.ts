export interface WorkerResponseModel {
    consecutiveLearningDayLimit: number;
    email: string;
    goals: [
        {
            id: number;
            topic: number;
            worker: number;
        }
    ];
    id: number;
    learningDays: [
        {
            assignee: {
                id: number;
            };
            comment: string;
            dateTimeAt: string;
            id: number;
            name: string;
            learned: boolean;
            topic: {
                childrenTopics: [
                    number
                ];
                description: string;
                id: number;
                name: string;
                roleGoals: number[];
                teamGoals: number[];
                workerGoals: number[];
            };
        }
    ];
    managedTeam: {
        id: number;
        name: string;
    };
    manager: {
        id: number;
        email: string;
        name: string;
        surname: string;
    };
    managerId: number;
    name: string;
    quarterLearningDayLimit: number;
    role: {
        id: number;
        name: string;
        roleGoals: number[];
    };
    surname: string;
    workingTeam: {
        id: number;
        name: string;
    };
}