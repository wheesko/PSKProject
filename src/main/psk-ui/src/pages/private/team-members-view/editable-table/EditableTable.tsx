import React, { useEffect, useState } from 'react';
import { Table, Popconfirm, Form, Tooltip, Button, Modal, Tag, Typography } from 'antd';
import { ExclamationCircleOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { Employee } from '../../../../models/employee';
import {
	DELETE_WARNING,
	DELETE_WARNING_INFO,
	EDIT_EMPLOYEE_DETAILS,
	REMOVE_EMPLOYEE
} from '../../../../constants/employeeConstants';
import './EditableTableStyles.css';
// import {myEmployees} from '../../../../tools/mockData';
import { THIS_ACTION_CANNOT_BE_UNDONE } from '../../../../constants/otherConstants';
import { EditableCell } from './EditableCell';
import { LearningEvent } from '../../../../models/learningEvent';
import { Role } from "../../../../models/role";
import { Link } from "react-router-dom";
import { UpdateLimitsRequest } from "../../../../api/model/update-limits-request";
import workerService from "../../../../api/worker-service";
import notificationService, { NotificationType } from "../../../../service/notification-service";
import history from '../../../../history';

const { confirm } = Modal;

interface EditableTableProps {
	employeeList: Employee[];
}

const EditableTable: React.FunctionComponent<EditableTableProps> = (props: EditableTableProps) => {
	const [form] = Form.useForm();
	const [data, setData] = useState<Employee[]>([]);
	useEffect(() => {
		setData(props.employeeList);
	}, [props.employeeList.length,history.location.pathname]);
	const [editingKey, setEditingKey] = useState(-1);

	const isEditing = (record: Employee) => record.id === editingKey;

	function updateLimits(limits: UpdateLimitsRequest, id: number): Promise<void> {
		return workerService.updateLimits(limits, id).then(() => {
			console.log("id: ", id);
			notificationService.notify({
				notificationType: NotificationType.SUCCESS,
				message: 'Limits update'
			});
		})
			.catch(() => {
				notificationService.notify({
					notificationType: NotificationType.ERROR,
					message: 'Could not update limits'
				});
			});
	}

	const edit = (record: Employee) => {
		form.setFieldsValue({ quarterConstraint: 0,  ...record });
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
				updateLimits({
					consecutiveLearningDayLimit: newData[index].consecutiveLearningDayLimit,
					quarterLearningDayLimit: newData[index].quarterLearningDayLimit
				}, item.id)
				console.log("Set data to: ", newData)
				setData(newData);
				setEditingKey(-1);
			} else {
				newData.push(row);
				console.log("Set data to: ", newData)
				setData(newData);
				// SEND UPDATE EMPLOYEE REQUEST HERE
				// dispatch(updateEmployee(row));
				updateLimits({
					consecutiveLearningDayLimit: newData[index].consecutiveLearningDayLimit,
					quarterLearningDayLimit: newData[index].quarterLearningDayLimit
				}, row.id)
				setEditingKey(-1);
			}
		} catch (errInfo) {
			console.log('Validate Failed:', errInfo);
		}
	};

	const columns = [
		{
			title: 'Full name',
			dataIndex: 'id',
			key: 'id',
			editable: false,
			render: (id: string, worker: Employee, index: number) => {
				return worker.name === null ?
					<Typography.Text disabled>Worker has not finished registration</Typography.Text> :
					<Link
						to={`/profile/${worker.id}`}>{`${worker.name} ${worker.surname}`}</Link>;
			},
		},
		{
			title: 'Email',
			dataIndex: 'email',
			key: 'email',
			editable: false,
		},
		{
			title: 'Team',
			dataIndex: 'team',
			key: 'team',
			editable: false,
		},
		{
			title: 'Quarter constraint',
			dataIndex: 'quarterLearningDayLimit',
			key: 'quarterLearningDayLimit',
			width: '8%',
			editable: true,
		},
		{
			title: 'Consecutive day constraint',
			dataIndex: 'consecutiveLearningDayLimit',
			key: 'consecutiveLearningDayLimit',
			width: '8%',
			editable: true
		},
		{
			title: 'Role',
			dataIndex: 'role',
			key: 'role',
			width: '12%',
			editable: false,
			render: (role: Role) => {
				return <Tag color={role.color}>{role.name}</Tag>

			}
		},
		{
			title: 'Action',
			key: 'action',
			width: '12%',
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
									onOk() {
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
