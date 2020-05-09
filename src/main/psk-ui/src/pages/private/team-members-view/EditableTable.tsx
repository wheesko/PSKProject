import React, { useState } from 'react';
import { Table, Input, InputNumber, Popconfirm, Form, Tooltip, Button, Modal } from 'antd';
import { ExclamationCircleOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { Employee } from '../../../models/employee';
import {
	DELETE_WARNING,
	DELETE_WARNING_INFO,
	EDIT_EMPLOYEE_DETAILS,
	REMOVE_EMPLOYEE
} from '../../../constants/employeeConstants';
import './EditableTableStyles.css';
import { myEmployees } from '../../../tools/mockData';
import { THIS_ACTION_CANNOT_BE_UNDONE } from '../../../Constants';

const { confirm } = Modal;

interface EditableCellProps extends React.HTMLAttributes<HTMLElement> {
	editing: boolean;
	dataIndex: string;
	title: string;
	inputType: number | string;
	record: Employee;
	index: number;
	children: React.ReactNode;
}

const EditableCell: React.FC<EditableCellProps> = ({
													   editing,
													   dataIndex,
													   title,
													   inputType,
													   record,
													   index,
													   children,
													   ...restProps
												   }: EditableCellProps) => {

	const inputNode = inputType === 'number' ? <InputNumber/> : <Input/>;

	return (
		<td {...restProps}>
			{editing ? (
				<Form.Item
					name={dataIndex}
					style={{ margin: 0 }}
					/*	you can add additional rules here */
				>
					{inputNode}
				</Form.Item>
			) : (
				children
			)}
		</td>
	);
};

const EditableTable = () => {
	const [form] = Form.useForm();

	const [data, setData] = useState(myEmployees);
	const [editingKey, setEditingKey] = useState(-1);

	const isEditing = (record: Employee) => record.id === editingKey;

	const edit = (record: Employee) => {
		form.setFieldsValue({ quarterConstraint: 0, goals: [], ...record });
		setEditingKey(record.id);
	};

	const cancel = () => {
		setEditingKey(-1);
		setEditingKey(-1);
	};
	const save = async (key: React.Key) => {
		try {
			const row = (await form.validateFields()) as Employee;

			const newData = [...data];
			const index = newData.findIndex(item => key === item.id);

			if (index > -1) {
				const item = newData[index];

				newData.splice(index, 1, {
					...item,
					...row,
				});
				// SEND UPDATE EMPLOYEE REQUEST HERE
				// dispatch(updateEmployee(item));
				setData(newData);
				setEditingKey(-1);
			} else {
				newData.push(row);
				setData(newData);
				// SEND UPDATE EMPLOYEE REQUEST HERE
				// dispatch(updateEmployee(row));
				setEditingKey(-1);
			}
		} catch (errInfo) {
			console.log('Validate Failed:', errInfo);
		}
	};

	const columns = [
		{
			title: 'Employee',
			dataIndex: 'name',
			key: 'name',
			editable: false,
			width: '17%',
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
			dataIndex: 'quarterConstraint',
			key: 'quarterConstraint',
			editable: true,
			width: '5%',
		},
		{
			title: 'Role',
			dataIndex: 'role',
			key: 'role',
			editable: true,
			width: '15%',
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
		{
			title: 'Action',
			key: 'action',
			width: '13%',
			render: (record: Employee): React.ReactNode => {
				const editable = isEditing(record);

				return editable ? (
					<span>
						<Button type="link" onClick={() => save(record.id)} style={{ marginRight: 8 }}>
              Save

						</Button>
						<Popconfirm title="Sure to cancel?" onConfirm={cancel}>
							<Button type="link">Cancel</Button>
						</Popconfirm>
					</span>
				) : (
					<>
						<Tooltip title={EDIT_EMPLOYEE_DETAILS}>
							<Button
								shape="round"
								size="small"
								icon={<EditOutlined/>}
								disabled={editingKey !== -1}
								onClick={() => edit(record)}
								className="editButton"/>
						</Tooltip>
						<Tooltip title={REMOVE_EMPLOYEE}>
							<Button
								shape="round"
								size="small"
								type="dashed"
								danger
								icon={<DeleteOutlined/>}
								onClick={() => confirm({
									width: 500,
									title: DELETE_WARNING + record.name + ' ?',
									icon: <ExclamationCircleOutlined/>,
									content: <>
										<span>
											{DELETE_WARNING_INFO}
										</span>
										<p>
											<b>{THIS_ACTION_CANNOT_BE_UNDONE}</b>
										</p>
									</>,
									okText: 'Yes',
									cancelText: 'No',
									onOk(){
										// on ok send delete request
										// and get current employee list
										console.log('OK');
									},
									onCancel() {
										console.log('Cancel');
									},
								})}
							/>
						</Tooltip>
					</>
				);
			}
		},
	];

	const mergedColumns = columns.map(col => {
		if (!col.editable) {
			return col;
		}
		return {
			...col,
			onCell: (record: Employee) => ({
				record,
				inputType: col.dataIndex === 'quarterConstraint' ? 'number' : 'text',
				dataIndex: col.dataIndex,
				title: col.title,
				editing: isEditing(record),
			}),
		};
	});

	return (
		<Form form={form} component={false}>
			<Table
				components={{
					body: {
						cell: EditableCell,
					},
				}}
				bordered
				dataSource={data}
				columns={mergedColumns}
				rowClassName="editable-row"
				pagination={{
					onChange: cancel,
				}}
			/>
		</Form>
	);
};

export { EditableTable };
