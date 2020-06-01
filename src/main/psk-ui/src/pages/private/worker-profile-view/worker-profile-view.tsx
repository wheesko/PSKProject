import React, { useEffect, useState } from 'react';
import { Card, Col, Row, Spin, Typography, Table, Button, Modal, Tag } from 'antd';
import { Link, RouteComponentProps } from 'react-router-dom';

import { WorkerResponseModel } from '../../../api/model/worker-response-model';
import workerService from '../../../api/worker-service';
import learningDayService from '../../../api/learning-day-service';

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
import { LearningEvent } from '../../../models/learningEvent';
import { Employee } from '../../../models/employee';
import { Role } from '../../../models/role';
import { getRoleColor } from '../../../tools/roleColorPicker';

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

const teamMemberColumns = [
	{
		title: 'Full name',
		dataIndex: 'id',
		key: 'id',
		editable: false,
		width: '17%',
		render: (id: string, worker: Employee, index: number) => {
			return worker.name === null
				? <Typography.Text disabled>Worker has not finished registration</Typography.Text>
				: <Link to={ `/profile/${ worker.id }` }>{ `${ worker.name } ${ worker.surname }` }</Link>;
		},
	},
	{
		title: 'Email',
		dataIndex: 'email',
		key: 'email',
		editable: false,
		width: '15%',
	},
	{
		title: 'Quarter constraint',
		dataIndex: 'quarterLearningDayLimit',
		key: 'quarterLearningDayLimit',
		width: '5%',
	},
	{
		title: 'Consecutive constraint',
		dataIndex: 'consecutiveLearningDayLimit',
		key: 'consecutiveLearningDayLimit',
		width: '5%',
	},
	{
		title: 'Role',
		dataIndex: 'role',
		key: 'role',
		editable: false,
		width: '15%',
		render: (role: any) => {
			return <Tag color={ getRoleColor(role.name) }>{ role.name }</Tag>;

		}
	}
];

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
	const [teamloading, setTeamLoading] = useState<boolean>(false);
	const [worker, setWorker] = useState<WorkerResponseModel>();
	const [isGoalModalVisible, setGoalModalVisible] = useState<boolean>(false);
	const [isEditLimitsModalVisible, setEditLimitsModalVisible] = useState<boolean>(false);
	const [teamLearningEvents, setTeamLearningEvents] = useState<LearningEvent[]>([]);
	const [teamMembers, setTeamMembers] = useState<any>(undefined);

	//component did mount
	useEffect(() => {
		loadData().then(worker => {

			setWorker(worker);
			return worker;
		})
			// @ts-ignore
			.then((worker) => {
				setTeamLoading(true);
				return worker.managedTeam ? Promise.all([
					learningDayService.getAllLearningDaysOfWorkersTeam(worker.id),
					workerService.getMembersOfTeam(worker.managedTeam.id)
				]) : [];
			})
			// @ts-ignore
			.then(([team, teamMembers]) => {
				setTeamMembers(teamMembers);
				setTeamLearningEvents(team);
				setLoading(false);
				setTeamLoading(false);
			})
			// @ts-ignore
			.catch((e) => {
				setLoading(false);
				setTeamLoading(false);
				notificationService.notify({
					description: e.response ? e.response.data.message : '',
					notificationType: NotificationType.ERROR,
					message: 'Could not find worker'
				});
			});
	}, [history.location.pathname]);

	return <Spin spinning={ loading }>
		{ !loading &&
        <>
        	<Row className={ 'profile-title-row' }>
        		<Col span={ 12 } style={ { textAlign: 'left' } }>
        			<Typography.Title level={ 2 }>Profile of { worker?.name } { worker?.surname }</Typography.Title>
        		</Col>
        		<Col span={ 12 } style={ { textAlign: 'right' } }>
        			<Tag color={ getRoleColor(worker?.role.name) }
        				className={ 'profile-role-tag ' }>{ worker?.role.name }</Tag>
        		</Col>
        	</Row>
        	{ renderInfoCard() }
        	<Row gutter={ 12 }>
        		<Col xs={ 24 } sm={ 24 } md={ 16 }>
        			{ renderLearningDays() }
        		</Col>
        		<Col xs={ 24 } sm={ 24 } md={ 8 }>
        			{ renderLearnedTopicsTable() }
        			{ renderAssignedGoalsCard() }
        		</Col>
        	</Row>
            <Spin spinning={teamloading}>
				{ (worker?.managedTeam && !teamloading && teamMembers ) &&
					<>
						<Typography.Title className="title" level={ 2 }>
							Worker { worker?.name } { worker?.surname } manages team {teamMembers.name}:
						</Typography.Title>
						<Row gutter={ 12 }>
							<Col xs={ 24 } sm={ 24 } md={ 16 }>
								{ renderTeamLearningDays() }
							</Col>
							<Col xs={ 24 } sm={ 24 } md={ 8 }>
								{ renderTeamLearnedTopicsTable() }
							</Col>
						</Row>
						<Row gutter={ 12 }>
							<Col xs={ 24 } sm={ 24 }>
								{ renderTeamMembersTable() }
							</Col>
						</Row>
					</>
                }
            </Spin>

        	{ renderAssignGoalsModal() }
        	{ renderEditLimitsModal() }
        </>
		}
	</Spin>;

	function onAssignClick() {
		setGoalModalVisible(true);
	}

	function renderAssignGoalsModal(): React.ReactNode {
		return <Modal
			visible={ isGoalModalVisible }
			footer={ null }
			onCancel={ modalClose }
		>
			<UserGoalForm onSubmit={ onSubmitGoal } workerId={ workerId }/>
		</Modal>;
	}

	function renderAssignedGoalsCard(): React.ReactNode {
		return <Card size="small" className="table-card">
			<Row justify="space-between">
				<Col>
					<Typography.Title level={ 4 }>Assigned goals</Typography.Title>
				</Col>
				<Col>
					<Button type="primary" onClick={ onAssignClick }>
						Assign goals <PlusOutlined/>
					</Button>
				</Col>
			</Row>
			<Table
				columns={ goalsColumns }
				dataSource={ worker?.goals }
			>
			</Table>
		</Card>;
	}

	function renderLearningDays(): React.ReactNode {
		return <Card className={ 'table-card' }>
			<Typography.Title level={ 4 }>Scheduled learning days</Typography.Title>
			<Table
				dataSource={ worker?.learningDays.filter(learningEvent => !learningEvent.learned) }
				columns={ workerLearningDayColumns }
			/>
		</Card>;
	}

	function renderTeamMembersTable(): React.ReactNode {
		return <Card className={ 'table-card' }>
			<Typography.Title level={ 4 }>Team members</Typography.Title>
			<Table
				dataSource={ teamMembers.workers }
				columns={ teamMemberColumns }
			/>
		</Card>;
	}


	function renderTeamLearningDays(): React.ReactNode {
		return <Card className={ 'table-card' }>
			<Typography.Title level={ 4 }>Team scheduled learning days</Typography.Title>
			<Table
				dataSource={ teamLearningEvents.filter(learningEvent => !learningEvent.learned) }
				columns={ workerLearningDayColumns }
			/>
		</Card>;
	}

	function renderTeamLearnedTopicsTable(): React.ReactNode {
		return <Card className={ 'table-card' }>
			<Typography.Title level={ 4 }>Team learned topics</Typography.Title>
			<Table
				dataSource={ teamLearningEvents.filter(learningEvent => learningEvent.learned)
					.map(learningEvent => learningEvent.topic)
				}
				columns={ learnedTopicsColumns }
			/>
		</Card>;
	}

	function renderManagedTeamTable(): React.ReactNode {
		return <Card className={ 'table-card' }>
			<Typography.Title level={ 4 }>Managed team</Typography.Title>
			<Table
				dataSource={ worker?.learningDays }
				columns={ workerLearningDayColumns }
			/>
		</Card>;
	}

	function renderInfoCard(): React.ReactNode {
		return <Card size="small" className="user-info-card">
			<Row justify="space-between">
				<Col>
					<Typography.Title level={ 4 }>Info</Typography.Title>
				</Col>
				<Col>
					<Button type="primary" onClick={ handleEditClick }>
						Edit limits <EditOutlined/>
					</Button>
				</Col>
			</Row>
			<Row>
				<Col xs={ 24 } sm={ 8 }>
					<Typography.Text className="bolded-text">Email: </Typography.Text>
					<Typography.Text>{ worker?.email }</Typography.Text>
				</Col>
			</Row>
			<Row>
				<Col xs={ 24 } sm={ 8 }>
					<Typography.Text className="bolded-text">
						Consecutive learning day limit:
					</Typography.Text>
					<Typography.Text>
						{ ' ' + worker?.consecutiveLearningDayLimit }
					</Typography.Text>
				</Col>
				<Col xs={ 24 } sm={ 8 }>
					<Typography.Text className="bolded-text">
						Quarterly learning day limit:
					</Typography.Text>
					<Typography.Text>
						{ ' ' + worker?.quarterLearningDayLimit }
					</Typography.Text>
				</Col>
			</Row>
			{ (worker?.manager !== null && worker?.manager.email !== email) &&
            <Row>
            	<Col xs={ 24 } sm={ 8 }>
            		<Link
            			type="link"
            			className="manager-link"
            			onClick={ () => history.push('/') }
            			to={ `profile/${ worker?.manager.id }` }
            		>
                        Manager:
            		</Link>
            		<Typography.Text>
            			{ ' ' + worker?.manager.name + ' ' + worker?.manager.surname }
            		</Typography.Text>
            	</Col>
            	<Col xs={ 24 } sm={ 8 }>
            		<Typography.Text className="bolded-text">
                        Manager email:
            		</Typography.Text>
            		<Typography.Text>
            			{ ' ' + worker?.manager.email }
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
		return <Card className={ 'table-card' }>
			<Typography.Title level={ 4 }>Learned topics</Typography.Title>
			<Table
				dataSource={ worker?.learningDays.map(learningDay => learningDay)
					.filter(learningEvent => learningEvent.learned)
					.map(learningEvent => learningEvent.topic)
				}
				columns={ learnedTopicsColumns }
			/>
		</Card>;
	}

	function loadData(): Promise<WorkerResponseModel> {
		setLoading(true);
		return workerService.getWorker(workerId);
	}

	function renderEditLimitsModal(): React.ReactNode {
		return <Modal footer={ null } visible={ isEditLimitsModalVisible } onCancel={ modalClose }>
			<UserLimitsForm workerId={ workerId }
				limitsQuarterly={ worker?.quarterLearningDayLimit ? worker?.quarterLearningDayLimit : 0 }
				limitsSequence={ worker?.consecutiveLearningDayLimit ? worker?.consecutiveLearningDayLimit : 0 }
				onSubmit={ onSubmitGoal }/>
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