import React, { useEffect, useState } from 'react';

import './TeamMembersStyles.css';
import { Button, Col, Modal, Row, Spin, Typography, Card, Table } from 'antd';

import { DONE } from '../../../constants/otherConstants';
import { PlusOutlined, } from '@ant-design/icons';
import { NewTeamMemberForm } from './NewTeamMemberForm';
import {
	ADD_NEW_EMPLOYEE,
	ADD_NEW_EMPLOYEES,
	CREATE_A_NEW_TEAM, CREATE_TEAM_INFO,
	YOUR_EMPLOYEES,
	YOUR_EMPLOYEES_WORKER,
	YOU_HAVE_NO_MANAGED_TEAM, FILTER_TEAM_MEMBERS_BY_TOPIC
} from '../../../constants/employeeConstants';
import { EditableTable } from './editable-table/EditableTable';
import { RootState } from '../../../redux';
import { useSelector } from 'react-redux';
import { Employee } from '../../../models/employee';
import workerService from '../../../api/worker-service';
import notificationService, { NotificationType } from '../../../service/notification-service';
import { Authority } from '../../../models/authority';
import { NewTeamForm } from './new-team-form';
import { WorkerTopicTable } from './worker-topic-table';
import { LearningEvent } from '../../../models/learningEvent';
import learningDayService from '../../../api/learning-day-service';
import moment from 'moment';
import { LearningTopic } from '../../../models/learningTopic';
import { ScheduledLearningDays } from './scheduled-learning-days';
import { LearnedTopicsTable } from './learned-topics-table';

const { Title } = Typography;

const TeamMembersView: React.FunctionComponent<{}> = () => {
	const [newTeamMemberModalVisibility, setNewTeamMemberModalVisibility] = useState<boolean>(false);
	const [newTeamModalVisibility, setNewTeamModalVisibility] = useState<boolean>(false);
	const currentWorker = useSelector((state: RootState) => state.user);
	const [isLoading, setLoading] = useState<boolean>(false);
	const [myEmployees, setMyEmployees] = useState<Employee[]>([]);
	const [teamLearningEvents, setTeamLearningEvents] = useState<LearningEvent[]>([]);


	function getManagedTeam() {
		setLoading(true);
		workerService.getEmployees().then(response => {
			setMyEmployees(response);
			setLoading(false);
		}).catch((error) => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: error.response.data.message !== null ? error.response.data.message : 'Failed to load employees',
				description: error.response.data.message === YOU_HAVE_NO_MANAGED_TEAM ? null : error.toString()
			});
			setLoading(false);
		});
	}

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
		getManagedTeam();
	}, [currentWorker.managedTeamId]);

	function handleOnOk(): void {
		setNewTeamMemberModalVisibility(false);
		setNewTeamModalVisibility(false);
	}

	function handleOnCancel(): void {
		setNewTeamMemberModalVisibility(false);
		setNewTeamModalVisibility(false);
	}

	function handleAdd(): void {
		setNewTeamMemberModalVisibility(true);
	}

	function handleCreateTeam(): void {
		setNewTeamModalVisibility(true);
	}

	function renderWorkerAuthority(): React.ReactNode {
		return <Card className={'teamMembersCard'}>
			<Title level={4} className={'teamMembersTitle'}>{YOUR_EMPLOYEES_WORKER}</Title>
			<Row gutter={[0, 12]}>
				<Col xs={24} sm={16}>
					<Typography.Text>
						{CREATE_TEAM_INFO}
					</Typography.Text>
				</Col>
			</Row>
			<Row gutter={[0, 12]}>
				<Col xs={24} sm={16}>
					<Button onClick={handleCreateTeam} type="primary" shape="round" icon={<PlusOutlined/>}>
						{CREATE_A_NEW_TEAM}
					</Button>
				</Col>
			</Row>
		</Card>;
	}

	function renderLeadAuthority(): React.ReactNode {
		return <>
			<Row justify={'start'}>
				<Typography.Title level={2}>Your managed team info</Typography.Title>
			</Row>
			<Row gutter={[12, 24]}>
				<Col xs={24} sm={24} md={16}>
					<ScheduledLearningDays teamLearningEvents={teamLearningEvents}/>
				</Col>
				<Col xs={24} sm={24} md={8}>
					<LearnedTopicsTable teamLearningEvents={teamLearningEvents}/>
				</Col>
			</Row>
			<Title level={2} className={'teamMembersTitle'}>
				{YOUR_EMPLOYEES}
			</Title>
			<Spin spinning={isLoading} size="large">
				<EditableTable employeeList={myEmployees}/>
			</Spin>
			<Row gutter={[0, 48]} justify="start">
				<Col xs={12} style={{ display: 'flex', justifyContent: 'start' }}>
					<Button onClick={handleAdd} type="primary" shape="round" icon={<PlusOutlined/>}>
						{ADD_NEW_EMPLOYEE}
					</Button>
				</Col>
			</Row>
			<Title level={2} className={'teamMembersTitle'}>{FILTER_TEAM_MEMBERS_BY_TOPIC}</Title>
			<WorkerTopicTable/>
		</>;
	}
	function loadData(): Promise<LearningEvent[]> {
		setLoading(true);
		return learningDayService.getAllLearningDaysOfTeam();
	}

	return <>
		{currentWorker.authority === Authority.LEAD ? renderLeadAuthority() : null}
		{currentWorker.authority === Authority.WORKER ? renderWorkerAuthority() : null}
		<Modal
			title={ADD_NEW_EMPLOYEES}
			visible={newTeamMemberModalVisibility}
			onOk={handleOnOk}
			onCancel={handleOnCancel}
			destroyOnClose
			cancelButtonProps={{ style: { display: 'none' } }}
			okText={DONE}
			afterClose={getManagedTeam}
		>
			<NewTeamMemberForm managerId={currentWorker.workerId}/>
		</Modal>
		<Modal
			title={'Creating a team'}
			visible={newTeamModalVisibility}
			onOk={handleOnOk}
			onCancel={handleOnCancel}
			destroyOnClose
			cancelButtonProps={{ style: { display: 'none' } }}
			okText={DONE}
		>
			<NewTeamForm id={currentWorker.workerId}/>
		</Modal>

	</>;
};

export { TeamMembersView };