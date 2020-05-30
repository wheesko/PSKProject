import React, { useEffect, useState } from 'react';
import { Card, Col, Row, Spin, Table, Tag, Typography } from 'antd';

import { connect } from 'react-redux';
import { UserState } from '../../../redux/user/types';
import { RootState } from '../../../redux';

import { LearningTopic } from '../../../models/learningTopic';
import notificationService, { NotificationType } from '../../../service/notification-service';
import workerService from '../../../api/worker-service';
import './ProfileStyles.css';
import { getRoleColor } from "../../../tools/roleColorPicker";

interface StateProps {
	user: UserState;
}

const goalsColumns = [
	{
		title: 'Topic name',
		dataIndex: 'name',
		key: 'name',
	},
	{
		title: 'Description',
		dataIndex: 'description',
		key: 'description',
	}
];

const ProfileViewComponent: React.FunctionComponent<StateProps> = (props: StateProps) => {
	const {
		user: {
			email,
			name,
			surname,
			role: {
				title
			}
		}
	} = props;
	const [topics, setTopics] = useState<LearningTopic[]>([]);
	const [loading, setLoading] = useState<boolean>(false);

	useEffect(() => {
		setLoading(true);
		loadTopics().then(topics => {
			setTopics(topics);
			setLoading(false);
		}).catch(e => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not get learned topics'
			});
		});
	}, []);

	return <Spin spinning={ loading }>
		<Row className={ "profile-title-row" }>
			<Col span={ 12 } style={{textAlign: 'left'}}>
				<Typography.Title level={ 2 }>Hello, { name } { surname }</Typography.Title>
			</Col>
			<Col span={ 12 } style={{textAlign: 'right'}}>
				<Tag color={ getRoleColor(title) } className={ "profile-role-tag " }>{ title }</Tag>
			</Col>
		</Row>
		{ renderMyInfoCard() }
		{ renderLearnedTopicsTable() }
	</Spin>;

	function renderMyInfoCard(): React.ReactNode {
		return <Card size="small" className="user-info-card">
			<Typography.Title level={ 4 }>Your info</Typography.Title>
			<Row>
				<Col xs={ 24 } sm={ 8 }>
					<Typography.Text>Email: { email }</Typography.Text>
				</Col>
			</Row>
		</Card>;
	}

	function renderLearnedTopicsTable(): React.ReactNode {
		return <Card className={ 'table-card' }>
			<Typography.Title level={ 4 }>Topics suggested for you</Typography.Title>
			<Table
				dataSource={ topics }
				columns={ goalsColumns }
			/>
		</Card>;
	}

	function loadTopics(): Promise<LearningTopic[]> {
		return workerService.getOwnGoals();
	}
};

const mapStateToProps = (state: RootState) => {
	return {
		user: state.user
	};
};

const ProfileView = connect(mapStateToProps)(ProfileViewComponent);

export { ProfileView };