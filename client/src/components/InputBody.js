import { useRef, useEffect } from 'react';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import { inputBodyState, inputBodyCheckState, nextState } from '../atom/atom';
import { useRecoilState, useSetRecoilState, useRecoilValue } from 'recoil';
import styled from 'styled-components';

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;

  .toastui-editor-defaultUI-toolbar {
    display: flex;
    flex-wrap: wrap;
  }
`;

const InputBody = () => {
  const [inputBody, setInputBody] = useRecoilState(inputBodyState);
  const setInputBodyCheck = useSetRecoilState(inputBodyCheckState);
  const editorRef = useRef();
  const next = useRecoilValue(nextState);

  const onChange = () => {
    setInputBody(editorRef.current.getInstance().getMarkdown());
  };

  const onblur = () => {
    if (inputBody === '') {
      setInputBodyCheck(false);
    } else {
      setInputBodyCheck(true);
    }
  };

  useEffect(() => {
    const htmlString = inputBody;
    editorRef.current?.getInstance().setMarkdown(htmlString);
  }, [next]);

  return (
    <Container>
      <Editor
        placeholder="본문을 입력해주세요"
        previewStyle="tab"
        height="600px"
        initialEditType="markdown"
        useCommandShortcut={false}
        toolbarItems={[
          ['heading', 'bold', 'italic', 'strike'],
          ['hr', 'quote'],
          ['ul', 'ol', 'task', 'indent', 'outdent'],
          ['table', 'link'],
          ['code', 'codeblock'],
        ]}
        language="ko-KR"
        ref={editorRef}
        onChange={onChange}
        onBlur={onblur}
        autofocus={false}
      />
    </Container>
  );
};

export default InputBody;
