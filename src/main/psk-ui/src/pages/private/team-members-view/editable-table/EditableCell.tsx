import React from 'react';
import { Form, Input, InputNumber } from 'antd';
import { Employee } from '../../../../models/employee';
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

export { EditableCell };