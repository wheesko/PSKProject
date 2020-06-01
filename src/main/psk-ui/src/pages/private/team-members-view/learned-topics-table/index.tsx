import React, { useEffect, useState } from 'react';
import { Card, Progress, Spin, Table, Typography } from 'antd';
import { TeamLearningEvents } from '../team-learning-events';
import topicService from '../../../../api/topic-service';
import { WorkerWithTopics } from '../../../../api/model/topic-by-manager-response';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import { LearningTopic } from '../../../../models/learningTopic';
import './LearnedTopicTableStyles.css';

const LearnedTopicsTable: React.FunctionComponent<TeamLearningEvents> = (props: TeamLearningEvents) => {
	const [isLoading, setIsLoading] = useState<boolean>(false);
	const [workersWithTopics, setWorkersWithTopics] = useState<WorkerWithTopics[]>([]);
	const learnedTopicsColumns = [
		{
			title: 'Name',
			dataIndex: 'name',
			key: 'name',
		},
		{
			title: 'Description',
			dataIndex: 'description',
			key: 'description',
		},
		{
			title: 'Learned workers',
			dataIndex: 'id',
			key: 'learnedWorkers',
			render: (id: number, learningTopic: LearningTopic, index: number) => {
				return <Progress type="circle" width={30}
								 percent={getTeamMemberCountWithLearnedTopic(learningTopic) / workersWithTopics.length * 100}
								 format={(percent, successPercent) => {
									 return `${getTeamMemberCountWithLearnedTopic(learningTopic)}`;
								 }}>
				</Progress>;
			}
		},
	];

	function getTeamMemberCountWithLearnedTopic(learningTopic: LearningTopic) {
		return workersWithTopics
			.filter(worker => worker.topicsPast
				.filter(topic => topic.learned))
			.filter(worker => worker.topicsPast
				.map(topic => {
					return topic.id;
				})
				.includes(learningTopic.id)).length;
	}

	useEffect(() => {
		getWorkersTopicsByManager();
	}, []);

	function getWorkersTopicsByManager() {
		setIsLoading(true);
		topicService.getWorkersTopicsByManager().then((response: WorkerWithTopics[]) => {
			setWorkersWithTopics(response);
			setIsLoading(false);
		}).catch((error) => {
			setIsLoading(false);
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to Employees',
				description: error.toString()
			});
		});
	}

	return <Card className={'table-card'}>
		<Typography.Title level={4}>This teams learned topics</Typography.Title>
		<Spin spinning={isLoading}>
			<Table
				dataSource={props.teamLearningEvents.filter(learningEvent => learningEvent.learned)
					//@ts-ignore
					.filter((v, i, a) => a.findIndex(t => (t.topic.name === v.topic.name)) === i)
					//@ts-ignore
					.map(learningEvent => learningEvent.topic)
				}
				rowKey="name"
				columns={learnedTopicsColumns}
			/>
		</Spin>
	</Card>;
};

export { LearnedTopicsTable };