import React, { useEffect, useState } from 'react';

import './TeamMembersStyles.css';
import { Button, Col, Modal, Row, Table, Typography } from 'antd';
import { ColumnProps } from 'antd/es/table';

import { DONE } from '../../../Constants';
import { Role } from '../../../models/role';
import {
	PlusOutlined,
} from '@ant-design/icons';
import { NewTeamMemberForm } from './NewTeamMemberForm';
import {ADD_NEW_EMPLOYEES, YOUR_EMPLOYEES} from '../../../constants/employeeConstants';
import { useDispatch, useSelector } from 'react-redux';
import { thunkLoadMyEmployees } from '../../../thunks';
import history from '../../../history';
import { RootState } from '../../../redux';
import { Employee } from '../../../redux/my-employees/types';

const { Title } = Typography;

const TeamMembersView: React.FunctionComponent<{}> = () => {
	// const workersRequest = useFetch('http://localhost:3000/workers/', []);
	const myEmployees = useSelector((state: RootState) => state.myEmployees);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(thunkLoadMyEmployees());
	},[history.location.pathname]);
	const [modalVisibility, setModalVisibility] = useState<boolean>(false);

	function handleOnOk(): void {
		setModalVisibility(false);
	}

	function handleOnCancel (): void {
		setModalVisibility(false);
	}

	const columns: ColumnProps<Employee>[] = [
		{
			title: 'Employee',
			dataIndex: 'name',
			key: 'name',
		},
		{
			title: 'Team',
			dataIndex: 'team',
			key: 'team',
		},
		{
			title: 'Quarter constraint',
			dataIndex: 'quarterConstraint',
			key: 'quarterConstraint',
		},
		{
			title: 'Role',
			dataIndex: 'role',
			key: 'role',
			// TODO: fix displaying role (need to create usable state interfaces)
			// render: (role: Role): React.ReactNode => {
			// 	return (
			// 		role === undefined ? null :
			// 			<Tag
			// 				color={role.color}
			// 				 key={role.title}>
			// 				{role.title.toUpperCase()}
			// 			</Tag>
			// 	);
			// }
		},
		{
			title: 'Goals',
			key: 'goals',
			dataIndex: 'goals',
			// TODO: fix displaying goals (need to create usable state interfaces)
			// render: (goals: Goal[]): React.ReactNode => (
			// 	<span>
			// 		{goals.map((goal: Goal) => {
			// 			let color = goals.length > 5 ? 'geekblue' : 'green';
			//
			// 			if (goal.name === 'loser') {
			// 				color = 'volcano';
			// 			}
			//
			// 			return (
			// 				<Tag color={color} key={goal.id}>
			// 					{goal.name.toUpperCase()}
			// 				</Tag>
			// 			);
			// 		})}
			// 	</span>
			// ),
		},
	];

	function handleAdd(): void {
		setModalVisibility(true);
	}

	return <>
		<Title level={2} className={'teamMembersTitle'}>{YOUR_EMPLOYEES}</Title>
		<Table<Employee> size={'large'}
			// we will use fetched data when available
			// dataSource={workersRequest.loading === true ? [] : workers}
					   dataSource={myEmployees === undefined ? undefined : myEmployees.dataSource}
					   columns={columns}
		/>
		<Row gutter={[0, 48]} justify="start"  >
			<Col xs={12}  style={{ display:'flex', justifyContent: 'start' }}>
				<Button onClick={handleAdd} type="primary" shape="round" icon={<PlusOutlined/>}>
					Add a new worker
				</Button>
			</Col>
		</Row>
		<Modal
			title={ADD_NEW_EMPLOYEES}
			visible={modalVisibility}
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