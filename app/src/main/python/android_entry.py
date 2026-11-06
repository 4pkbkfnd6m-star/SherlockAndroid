import contextlib
import io
import shlex
import sys
import traceback

def run(raw_usernames):
    names = shlex.split(raw_usernames.replace("\n", " "))
    if not names:
        return "请输入至少一个用户名。"

    stdout = io.StringIO()
    stderr = io.StringIO()
    old_argv = sys.argv[:]
    try:
        sys.argv = ["sherlock", *names, "--print-found", "--no-color"]
        with contextlib.redirect_stdout(stdout), contextlib.redirect_stderr(stderr):
            from sherlock_project.sherlock import main
            main()
    except SystemExit as exc:
        if exc.code not in (None, 0):
            stderr.write("\nSherlock 退出代码：{}".format(exc.code))
    except Exception:
        stderr.write("\n" + traceback.format_exc())
    finally:
        sys.argv = old_argv

    result = stdout.getvalue()
    errors = stderr.getvalue()
    combined = result
    if errors:
        combined += "\n\n--- 日志 / 错误 ---\n" + errors
    if not combined.strip():
        combined = "查询结束，但没有捕获到文本输出。"
    return combined
