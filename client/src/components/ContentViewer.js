import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/react-editor';
import { useEffect, useRef } from 'react';
import styled from 'styled-components';

const RightViewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: white;
  border-radius: 8px;
  padding: 30px;
  margin: 25px 0 25px 15px;
  .content-plan {
    margin-bottom: 20px;
    padding-bottom: 20px;
    font-size: 15px;
    font-weight: 500;
    color: var(--grey-dark);
    border-bottom: 1px solid var(--purple-medium);
  }
`;
const ContentViewer = ({ content }) => {
  const viewerRef = useRef();

  useEffect(() => {
    viewerRef.current && viewerRef.current.getInstance().setMarkdown(content);
  });
  return (
    <RightViewContainer>
      <span className="content-plan">프로젝트 계획을 설명해 주세요!</span>
      <Viewer ref={viewerRef} initialValue={content}></Viewer>
    </RightViewContainer>
  );
};

export default ContentViewer;
