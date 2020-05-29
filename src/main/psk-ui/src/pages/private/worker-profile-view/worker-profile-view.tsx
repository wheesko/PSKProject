import React, { useEffect, useState } from 'react';
import { Card, Col, Row, Spin, Typography, Table, Button, Modal } from 'antd';
import { Link, RouteComponentProps } from 'react-router-dom';

import { WorkerResponseModel } from '../../../api/model/worker-response-model';
import workerService from '../../../api/worker-service';
import notificationService, { NotificationType } from '../../../service/notification-service';
import moment from 'moment';
import { LearningTopic } from '../../../models/learningTopic';

import { EditOutlined, PlusOutlined } from '@ant-design/icons';
import history from '../../../history';

import './WorkerProfileStyles.css';
import { connect } from 'react-redux';
import { RootState } from '../../../redux';
import { UserState } from '../../../redux/user/types';
import { UserGoalForm } from './form/user-goal-form';
import { UserLimitsForm } from './form/user-limits-form';

interface WorkerProfileViewProps extends RouteComponentProps {
    workerId?: string | undefined;
}

interface StateProps {
	user: UserState;
}

type Props = WorkerProfileViewProps & StateProps;

const workerLearningDayColumns = [
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

const teamColumns = [];

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

const WorkerProfileViewComponent: React.FunctionComponent<Props> = (props: Props) => {
	const { params } = props.match;
	//@ts-ignore
	const { workerId } = params;

	const {
		user: {
			email
		}
	} = props;
	
	const [loading, setLoading] = useState<boolean>(false);
	const [worker, setWorker] = useState<WorkerResponseModel>();
	const [isGoalModalVisible, setGoalModalVisible] = useState<boolean>(false);
	const [isEditLimitsModalVisible, setEditLimitsModalVisible] = useState<boolean>(false);

	//component did mount
	useEffect(() => {
		loadData().then(worker => {
			setWorker(worker);
			setLoading(false);
			return worker;
		})
			.catch((e) => {
		    notificationService.notify({
					description: e.response ? e.response.data.message : '',
					notificationType: NotificationType.ERROR,
					message: 'Could not find worker'
				});
			});
	}, [history.location.pathname]);
    
	return <Spin spinning={loading}>
		{!loading &&
		<>
			<Typography.Title level={2}>Profile of {worker?.name} {worker?.surname}</Typography.Title>
			{renderInfoCard()}
			<Row gutter={12}>
				<Col xs={24} sm={24} md={16}>
					{renderLearningDays()}
				</Col>
				<Col xs={24} sm={24} md={8}>
					{renderLearnedTopicsTable()}
					{renderAssignedGoalsCard()}
				</Col>
			</Row>
			{worker?.managerId ? renderManagedTeamTable() : null}
			{renderAssignGoalsModal()}
			{renderEditLimitsModal()}
		</>
		}
	</Spin>;

	function onAssignClick() {
		setGoalModalVisible(true);
	}

	function renderAssignGoalsModal(): React.ReactNode {
		return <Modal
			visible={isGoalModalVisible}
		  	footer={null}
			onCancel={modalClose}
		>
			<UserGoalForm onSubmit={onSubmitGoal} workerId={workerId}/>
		</Modal>;
	}

	function renderAssignedGoalsCard(): React.ReactNode {
		return <Card size="small" className="table-card">
			<Row justify="space-between">
				<Col>
					<Typography.Title level={4}>Assigned goals</Typography.Title>
				</Col>
				<Col>
					<Button type="primary" onClick={onAssignClick}>
						Assign goals <PlusOutlined/>
					</Button>
				</Col>
			</Row>
			<Table
				columns={goalsColumns}
				dataSource={worker?.goals}
			>
			</Table>
		</Card>;
	}

	function renderLearningDays(): React.ReactNode {
		return <Card className={'table-card'}>
			<Typography.Title level={4}>Scheduled learning days</Typography.Title>
			<Table
				dataSource={worker?.learningDays.filter(learningEvent => !learningEvent.learned)}
				columns={workerLearningDayColumns}
			/>
		</Card>;
	}
    
	function renderManagedTeamTable(): React.ReactNode {
		return <Card className={'table-card'}>
			<Typography.Title level={4}>Managed team</Typography.Title>
			<Table
				dataSource={worker?.learningDays}
				columns={workerLearningDayColumns}
			/>
		</Card>;
	}

	function renderInfoCard(): React.ReactNode {
		return <Card size="small" className="user-info-card">
			<Row justify="space-between">
				<Col>
					<Typography.Title level={4}>Info</Typography.Title>
				</Col>
				<Col>
					<Button type="primary" onClick={handleEditClick}>
						Edit limits <EditOutlined/>
					</Button>
				</Col>
			</Row>
			<Row>
				<Col xs={24} sm={8}>
					<Typography.Text className="bolded-text">Email: </Typography.Text>
					<Typography.Text>{worker?.email}</Typography.Text>
				</Col>
				<Col xs={24} sm={8}>
					<Typography.Text className="bolded-text">Role: </Typography.Text>
					<Typography.Text>{worker?.role.name}</Typography.Text>
				</Col>
			</Row>
			<Row>
				<Col xs={24} sm={8}>
					<Typography.Text  className="bolded-text">
						Consecutive learning day limit:
					</Typography.Text>
					<Typography.Text>
						{' ' + worker?.consecutiveLearningDayLimit}
					</Typography.Text>
				</Col>
				<Col xs={24} sm={8}>
					<Typography.Text  className="bolded-text">
						Quarterly learning day limit:
					</Typography.Text>
					<Typography.Text>
						{' ' +worker?.quarterLearningDayLimit}
					</Typography.Text>
				</Col>
			</Row>
			{(worker?.manager !== null && worker?.manager.email !== email) &&
			<Row>
				<Col xs={24} sm={8}>
					<Link
						type="link"
						className="manager-link"
						onClick={() => history.push('/')}
						to={`profile/${worker?.manager.id}`}
					>
							Manager:
					</Link>
					<Typography.Text>
						{' ' + worker?.manager.name + ' ' +  worker?.manager.surname}
					</Typography.Text>
				</Col>
				<Col xs={24} sm={8}>
					<Typography.Text  className="bolded-text">
						Manager email:
					</Typography.Text>
					<Typography.Text>
						{' ' +  worker?.manager.email}
					</Typography.Text>
				</Col>
			</Row>
			}
		</Card>;
	}

	function handleEditClick() {
		setEditLimitsModalVisible(true);
	}

	function modalClose() {
		setEditLimitsModalVisible(false);
		setGoalModalVisible(false);
	}


	function renderLearnedTopicsTable(): React.ReactNode {
		return <Card className={'table-card'}>
			<Typography.Title level={4}>Learned topics</Typography.Title>
			<Table
				dataSource={worker?.learningDays.map(learningDay => learningDay)
					.filter(learningEvent => learningEvent.learned)
					.map(learningEvent => learningEvent.topic)
				}
				columns={learnedTopicsColumns}
			/>
		</Card>;
	}
	
	function loadData(): Promise<WorkerResponseModel> {
	    setLoading(true);
	    return workerService.getWorker(workerId);
	}

	function renderEditLimitsModal(): React.ReactNode {
		return <Modal footer={null} visible={isEditLimitsModalVisible} onCancel={modalClose}>
			<UserLimitsForm workerId={workerId} 
				limitsQuarterly={worker?.quarterLearningDayLimit ? worker?.quarterLearningDayLimit: 0}
				limitsSequence={worker?.consecutiveLearningDayLimit ? worker?.consecutiveLearningDayLimit : 0 }
				onSubmit={onSubmitGoal}/>
		</Modal>;
	}

	function onSubmitGoal() {
		loadData().then(worker => {
			setWorker(worker);
			setLoading(false);
			setGoalModalVisible(false);
			setEditLimitsModalVisible(false);
			return worker;
		})
			.catch((e) => {
				notificationService.notify({
					description: e.response ? e.response.data.message : '',
					notificationType: NotificationType.ERROR,
					message: 'Could not find worker'
				});
			});
	}
};

const mapStateToProps = (state: RootState) => {
	return {
		user: state.user
	};
};

const WorkerProfileView = connect(mapStateToProps)(WorkerProfileViewComponent);

export { WorkerProfileView };