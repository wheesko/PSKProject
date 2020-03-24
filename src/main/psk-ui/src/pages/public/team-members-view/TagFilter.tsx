import {Tag, Input, Tooltip} from 'antd';
import {PlusOutlined} from '@ant-design/icons';
import React, {useState, useRef, ChangeEvent, useEffect} from "react";

const TagFilter: React.FunctionComponent<{}> = () => {
	const inputEl = useRef(null);
	const [tags, setTags] = useState<string[]>([]);
	const [inputVisible, setInputVisible] = useState<boolean>(false);
	const [inputValue, setInputValue] = useState<string>('');

	const handleClose = (removedTag: string): void => {
		setTags(tags.filter(tag => tag !== removedTag));
	};
	useEffect(() => {
		// @ts-ignore
		if (inputEl.current)
			// inputEl.current.focus();

		console.log(inputEl);
	},[inputEl])
	const showInput = (): void => {
		setInputVisible(true);
		// @ts-ignore
		inputEl.current.focus();
	};

	// @ts-ignore
	const handleInputChange = (e): void => {
		setInputValue(e.target.value);
	};

	const handleInputConfirm = (): void => {

			if (inputValue && tags.indexOf(inputValue) === -1) {
				setTags([...tags, inputValue]);
				setInputVisible(false);
				setInputValue('');
			}
		}
	;
	return (
		<div>
			{tags.map((tag, index) => {
				const isLongTag = tag.length > 20;
				const tagElem = (
					<Tag key={tag} closable={index !== 0} onClose={() => handleClose(tag)}>
						{isLongTag ? `${tag.slice(0, 20)}...` : tag}
					</Tag>
				);
				return isLongTag ? (
					<Tooltip title={tag} key={tag}>
						{tagElem}
					</Tooltip>
				) : (
					tagElem
				);
			})}
			{inputVisible && (
				<Input
					ref={inputEl}
					type="text"
					size="small"
					style={{width: 78}}
					value={inputValue}
					onChange={(e:ChangeEvent) => handleInputChange(e)}
					onBlur={handleInputConfirm}
					onPressEnter={handleInputConfirm}
				/>
			)}
			{!inputVisible && (
				<Tag className="site-tag-plus" onClick={showInput}>
					<PlusOutlined/> New Filter
				</Tag>
			)}
		</div>
	);
}

export default TagFilter;