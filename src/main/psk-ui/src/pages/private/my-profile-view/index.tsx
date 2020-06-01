import React, { useEffect, useState } from 'react';
import { Card, Col, Row, Spin, Table, Tag, Typography } from 'antd';

import { connect } from 'react-redux';
import { UserState } from '../../../redux/user/types';
import { RootState } from '../../../redux';

import { LearningTopic } from '../../../models/learningTopic';
import notificationService, { NotificationType } from '../../../service/notification-service';
import workerService from '../../../api/worker-service';
import './ProfileStyles.css';
import { getRoleColor } from '../../../tools/roleColorPicker';
import { ProfileResponseModel } from '../../../api/model/profile-response-model';

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
			role
		}
	} = props;
	const [topics, setTopics] = useState<LearningTopic[]>([]);
	const [loading, setLoading] = useState<boolean>(false);
	const [profile, setProfile] = useState<ProfileResponseModel>();

	useEffect(() => {
		setLoading(true);
		loadProfile().then(([topics, profile] )=> {
			setTopics(topics);
			setProfile(profile);
			setLoading(false);
		}).catch(e => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not get learned topics'
			});
		});
	}, []);

	return <Spin spinning={ loading }>
		<Row className={ 'profile-title-row' }>
			<Col span={ 12 } style={{ textAlign: 'left' }}>
				<Typography.Title level={ 2 }>Hello, { name } { surname }</Typography.Title>
			</Col>
			<Col span={ 12 } style={{ textAlign: 'right' }}>
				<Tag color={ getRoleColor(role.name) } className={ 'profile-role-tag ' }>{ role.name }</Tag>
			</Col>
		</Row>
		{ renderMyInfoCard() }
		{ renderLearnedTopicsTable() }
	</Spin>;

	function renderMyInfoCard(): React.ReactNode {
		return <Card size="small" className="user-info-card">
			<Typography.Title level={ 4 }>Your info</Typography.Title>
			<Row justify="space-between">
				<Col xs={ 24 } sm={ 8 }>
					<Typography.Paragraph>Email: { email }</Typography.Paragraph>
					<Typography.Paragraph>
						Consecutive learning day limit: {profile?.consecutiveLearningDayLimit}
					</Typography.Paragraph>
					<Typography.Paragraph>
						Quarterly learning day limit: {profile?.quarterlyLearningDayLimit}
					</Typography.Paragraph>
				</Col>
				{profile?.manager &&
					<Col xs={24} sm={8}>
						<Typography.Paragraph>
							Your manager: {profile?.manager.name + ' ' + profile?.manager.surname + ' '}
						</Typography.Paragraph>
						<Typography.Paragraph>
							Email: {profile?.manager.email}
						</Typography.Paragraph>
					</Col>
				}
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

	function loadProfile(): Promise<[LearningTopic[], ProfileResponseModel]> {
		return Promise.all([workerService.getOwnGoals(), workerService.getOwnProfile()]);
	}
};

const mapStateToProps = (state: RootState) => {
	return {
		user: state.user
	};
};

const ProfileView = connect(mapStateToProps)(ProfileViewComponent);

export { ProfileView };