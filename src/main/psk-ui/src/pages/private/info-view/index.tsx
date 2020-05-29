import React, { useEffect, useState } from 'react';

import { Card, Col, Row, Spin, Table, Typography } from 'antd';

import './InfoStyles.css';
import notificationService, { NotificationType } from '../../../service/notification-service';

import { LearningEvent } from '../../../models/learningEvent';
import learningDayService from '../../../api/learning-day-service';
import moment from 'moment';
import { LearningTopic } from '../../../models/learningTopic';

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
	}
];

const workerLearningDayColumns = [
	{
		title: 'Assignee',
		dataIndex: 'assignee',
		key: 'assignee',
		render: (assignee: any) => {
			return assignee.name + ' ' + assignee.surname;
		}
	},
	{
		title: 'Name',
		dataIndex: 'name',
		key: 'name',
	},
	{
		title: 'Date',
		dataIndex: 'dateTimeAt',
		key: 'dateTimeAt',
		render: (date: string) => moment(date).format('yyyy-MM-DD')
	},
	{
		title: 'Topic',
		dataIndex: 'topic',
		key: 'topic',
		render: (topic: LearningTopic) => topic.name
	},
	{
		title: 'Topic description',
		dataIndex: 'topic',
		key: 'topic',
		render: (topic: LearningTopic) => topic.description
	}
];

const InfoView: React.FunctionComponent<{}> = () => {
	const [loading, setLoading] = useState<boolean>(false);
	const [teamLearningEvents, setTeamLearningEvents] = useState<LearningEvent[]>([]);

	//component did mount
	useEffect(() => {
		loadData().then(events => {
			setTeamLearningEvents(events);
			setLoading(false);
			return teamLearningEvents;
		})
			.catch((e) => {
				notificationService.notify({
					description: e.response ? e.response.data.message : '',
					notificationType: NotificationType.ERROR,
					message: 'Could not load team events'
				});
			});
	}, []);

	return <Spin spinning={loading}>
		{!loading &&
		<>
			<Typography.Title level={2}>Your team info</Typography.Title>
			<Row gutter={12}>
				<Col xs={24} sm={24} md={16}>
					{renderLearningDays()}
				</Col>
				<Col xs={24} sm={24} md={8}>
					{renderLearnedTopicsTable()}
				</Col>
			</Row>
		</>
		}
	</Spin>;

	function renderLearningDays(): React.ReactNode {
		return <Card className={'table-card'}>
			<Typography.Title level={4}>Scheduled learning days</Typography.Title>
			<Table
				dataSource={teamLearningEvents.filter(learningEvent => !learningEvent.learned)}
				columns={workerLearningDayColumns}
			/>
		</Card>;
	}

	function renderLearnedTopicsTable(): React.ReactNode {
		return <Card className={'table-card'}>
			<Typography.Title level={4}>Learned topics</Typography.Title>
			<Table
				dataSource={teamLearningEvents
					.filter(learningEvent => learningEvent.learned)
					.map(learningEvent => learningEvent.topic)
				}
				columns={learnedTopicsColumns}
			/>
		</Card>;
	}

	function loadData(): Promise<LearningEvent[]> {
		setLoading(true);
		return learningDayService.getAllLearningDaysOfTeam();
	}
};

export { InfoView };