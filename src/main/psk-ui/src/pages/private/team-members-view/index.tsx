import React, { useEffect, useState } from 'react';

import './TeamMembersStyles.css';
import { Button, Col, Modal, Row,Typography } from 'antd';

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

const { Title } = Typography;

const TeamMembersView: React.FunctionComponent<{}> = () => {
	const [newTeamMemberModalVisibility, setNewTeamMemberModalVisibility] = useState<boolean>(false);


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
		<EditableTable/>
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
		>
			<NewTeamMemberForm/>
		</Modal>
	</>;
};

export { TeamMembersView };