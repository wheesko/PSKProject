import React, { useEffect, useState } from 'react';

import './TeamMembersStyles.css';
import { Button, Col, Modal, Row, Spin, Typography } from 'antd';

import { DONE } from '../../../constants/otherConstants';
import {
	PlusOutlined,
} from '@ant-design/icons';
import { NewTeamMemberForm } from './NewTeamMemberForm';
import {
	ADD_NEW_EMPLOYEE,
	ADD_NEW_EMPLOYEES,
	YOUR_EMPLOYEES
} from '../../../constants/employeeConstants';
import { EditableTable } from './editable-table/EditableTable';
import { RootState } from '../../../redux';
import { useSelector } from 'react-redux';
import { Employee } from '../../../models/employee';
import workerService from '../../../api/worker-service';
import notificationService, { NotificationType } from '../../../service/notification-service';

const { Title } = Typography;

const TeamMembersView: React.FunctionComponent<{}> = () => {
	const [newTeamMemberModalVisibility, setNewTeamMemberModalVisibility] = useState<boolean>(false);
	const currentWorker = useSelector((state: RootState) => state.user);
	const [isLoading, setLoading] = useState<boolean>(false);
	const [myEmployees, setMyEmployees] = useState<Employee[]>([]);

	function getManagedTeam() {
		setLoading(true);
		workerService.getEmployees().then(response => {
			setMyEmployees(response);
			setLoading(false);
		}).catch((error) => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to Employees',
				description: error.toString()
			});
			setLoading(false);
		});
	}

	useEffect(() => {
		getManagedTeam();
	}, [currentWorker.managedTeamId]);

	function handleOnOk(): void {
		setNewTeamMemberModalVisibility(false);
	}

	function handleOnCancel(): void {
		setNewTeamMemberModalVisibility(false);
	}

	function handleAdd(): void {
		setNewTeamMemberModalVisibility(true);
	}


	return <>
		<Title level={2} className={'teamMembersTitle'}>{YOUR_EMPLOYEES}</Title>
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
	</>;
};

export { TeamMembersView };