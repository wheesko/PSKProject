import React, { useEffect, useState } from 'react';
import treeService from '../../../api/tree-service';
import Tree from 'react-d3-tree';
import { Spin } from 'antd';

import './tree-styles.css';


const TopicTreeView: React.FunctionComponent<{}> = () => {

	const [treeData, setTreeData] = useState<any>(undefined);
	const [loading, setLoading] = useState<boolean>(false);

	useEffect(() => {
		setLoading(true);
		treeService.getTree().then(response =>{
			//@ts-ignore
			setTreeData(response);
			setLoading(false);
		});
	}, []);


	return <Spin spinning={loading}>
		{treeData &&
		<div className="treeWrapper">
			<Tree data={treeData} />
		</div>
		}
	</Spin>;
};

export { TopicTreeView };