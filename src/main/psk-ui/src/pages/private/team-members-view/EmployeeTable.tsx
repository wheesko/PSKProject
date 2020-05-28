import React, { useEffect, useState } from 'react';
import { Table, Popconfirm, Form, Tooltip, Button, Modal, Tag } from 'antd';
import { ExclamationCircleOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { Employee } from '../../../models/employee';
import { LearningEvent } from '../../../models/learningEvent';
import { Role } from '../../../models/role';

const { confirm } = Modal;

interface TableProps {
	employeeList: Employee[];
}

const EmployeeTable: React.FunctionComponent<TableProps> = (props: TableProps) => {
	const [data, setData] = useState<Employee[]>([]);

	useEffect(() => {
		setData(props.employeeList);
	}, [props.employeeList.length]);

	const columns = [
		{
			title: 'Name',
			dataIndex: 'name',
			key: 'name',
			editable: false,
			width: '8.5%',
		},
		{
			title: 'Surname',
			dataIndex: 'surname',
			key: 'surname',
			editable: false,
			width: '8.5%',
		},
		{
			title: 'Email',
			dataIndex: 'email',
			key: 'email',
			editable: false,
			width: '15%',
		},
		{
			title: 'Team',
			dataIndex: 'team',
			key: 'team',
			editable: false,
			width: '15%',
		},
		{
			title: 'Quarter constraint',
			dataIndex: 'quarterLearningDayLimit',
			key: 'quarterLearningDayLimit',
			editable: true,
			width: '5%',
		},
		{
			title: 'Role',
			dataIndex: 'role',
			key: 'role',
			editable: true,
			width: '15%',
			render: (role: Role) => {
				return <Tag color={role.color}>{role.title}</Tag>;

			}
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
			editable: true,
			width: '20%',
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

	return (
		<Table
			bordered
			dataSource={data}
			columns={columns}
		/>
	);
};

export { EmployeeTable };
